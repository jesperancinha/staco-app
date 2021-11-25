package org.jesperancinha.enterprise.staco.s3

import mu.KotlinLogging
import org.apache.commons.csv.CSVFormat.DEFAULT
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import org.jesperancinha.enterprise.staco.common.aws.AwsProperties.Companion.STACOS_BUCKET
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.toEvent
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.dynamodb.domain.StaCoDynamoDBRepository
import org.jesperancinha.enterprise.staco.jpa.domain.StaCo
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.core.async.AsyncResponseTransformer
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.ListObjectsRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Files.newBufferedWriter
import java.nio.file.Path
import java.time.Instant
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream


@Component
class AwsStacoFileService(
    private val s3AsyncClient: S3AsyncClient,
    private val staCoDynamoDBRepository: StaCoDynamoDBRepository
) {

    private val logger = KotlinLogging.logger {}

    fun createCompressAndUploadToS3(stacos: List<StaCo>) {
        val fileName = "stacos-${Instant.now().toEpochMilli()}"
        val path = Files.createTempFile(fileName, ".csv")
        val writer = newBufferedWriter(path)

        val csvPrinter = CSVPrinter(
            writer, CSV_HEADER
        )
        stacos.forEach {
            csvPrinter.printRecord(
                it.stacoId,
                it.description,
                it.year,
                it.value,
                it.currency,
                it.diameterMM,
                it.internalDiameterMM,
                it.heightMM,
                it.widthMM
            )
        }
        writer.flush()
        writer.close()
        val output: Path = Files.createTempFile(fileName, ".gz")

        compressGZIP(path, output)
        val fileIn: InputStream = Files.newInputStream(output)
        val size: Long = Files.size(output)
        s3AsyncClient.putObject(
            PutObjectRequest.builder().bucket(STACOS_BUCKET).key(fileName)
                .metadata(
                    mapOf(
                        "Content-Type" to "application/x-gzip",
                        "Content-Length" to size.toString()
                    )
                ).build(),
            AsyncRequestBody.fromBytes(fileIn.readBytes())
        )
        logger.info { "Upload underway!" }
        logger.info { "File $path was created!" }
        logger.info { "File $output was uploaded!" }

    }

    fun downloadFileFromS3UpdateDynamoDBAndDelete() {
        s3AsyncClient.listObjects(
            ListObjectsRequest.builder().bucket(STACOS_BUCKET).build()
        ).thenApplyAsync { listObjectResponse ->
            listObjectResponse.contents().map { s3Object ->
                logger.info { "Processing object file ${s3Object.key()}" }
                val targetFileKey = s3Object.key()
                val path = Files.createTempFile(targetFileKey, ".gz")
                s3AsyncClient.getObject(
                    GetObjectRequest.builder().bucket(STACOS_BUCKET).key(targetFileKey).build(),
                    AsyncResponseTransformer.toBytes()
                )
                    .thenApplyAsync {
                        try {
                            Files.write(path, it.asByteArray())
                            logger.info { "Downloaded file $path" }
                            val output = Files.createTempFile(targetFileKey, ".csv")
                            decompressGzip(path, output)
                            val reader = Files.newBufferedReader(output)
                            val csvParser = CSVParser(reader, CSV_HEADER)
                            val records = csvParser.records
                            for (csvRecord in records.takeLast(records.size - 1)) {
                                staCoDynamoDBRepository.save(
                                    StaCoDto(
                                        id = csvRecord.get("id"),
                                        description = Description(csvRecord.get("description")),
                                        year = csvRecord.get("year"),
                                        value = csvRecord.get("value"),
                                        currency = CurrencyType.valueOf(csvRecord.get("currency")),
                                        diameterMM = csvRecord.get("diameterMM"),
                                        internalDiameterMM = csvRecord.get("internalDiameterMM"),
                                        heightMM = csvRecord.get("heightMM"),
                                        widthMM = csvRecord.get("widthMM")
                                    ).toEvent
                                )
                            }
                            logger.info { "Download and parsing of file $output complete!" }
                            removeResource(targetFileKey)
                        } catch (ex: Exception) {
                            removeResource(targetFileKey)
                            logger.info { "File with Key $targetFileKey is invalid and has been removed!" }
                            logger.error { ex }
                        }
                    }
            }
        }
    }

    private fun removeResource(targetFileKey: String?) = s3AsyncClient.deleteObject(
        DeleteObjectRequest.builder().bucket(STACOS_BUCKET).key(targetFileKey).build()
    )

    fun compressGZIP(input: Path, output: Path) {
        GZIPOutputStream(FileOutputStream(output.toFile())).use { out ->
            FileInputStream(input.toFile()).use { `in` ->
                val buffer = ByteArray(1024)
                var len: Int
                while (`in`.read(buffer).also { len = it } != -1) {
                    out.write(buffer, 0, len)
                }
            }
        }
    }

    fun decompressGzip(input: Path, output: Path) {
        GZIPInputStream(FileInputStream(input.toFile())).use { `in` ->
            FileOutputStream(output.toFile()).use { out ->
                val buffer = ByteArray(1024)
                var len: Int
                while (`in`.read(buffer).also { len = it } != -1) {
                    out.write(buffer, 0, len)
                }
            }
        }
    }

    companion object {
        private val CSV_HEADER = DEFAULT
            .withHeader(
                "id",
                "description",
                "year",
                "value",
                "currency",
                "diameterMM",
                "internalDiameterMM",
                "heightMM",
                "widthMM"
            )
    }
}
