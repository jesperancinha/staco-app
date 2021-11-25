package org.jesperancinha.enterprise.staco.s3

import mu.KotlinLogging
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream
import org.apache.commons.compress.utils.IOUtils
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import org.jesperancinha.enterprise.staco.jpa.domain.StaCo
import org.springframework.stereotype.Component
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Files.newBufferedWriter
import java.nio.file.Path
import java.time.Instant


@Component
class AwsStacoFileService(
    private val s3AsyncClient: S3AsyncClient,
) {

    private val logger = KotlinLogging.logger {}

    fun createCompressAndUploadToS3(stacos: List<StaCo>) {
        val fileName = "stacos-${Instant.now().toEpochMilli()}"
        val path = Files.createTempFile(fileName, ".csv")
        val writer = newBufferedWriter(path)
        val csvPrinter = CSVPrinter(
            writer, CSVFormat.DEFAULT
                .withHeader("ID")
        )
        stacos.forEach {
            csvPrinter.printRecord(it.id)
        }
        writer.flush()
        writer.close()
        val inputStream = Files.newInputStream(path)
        val tmpPath: Path = Files.createTempFile(fileName, "gz")
        val out: OutputStream = Files.newOutputStream(tmpPath)
        val gzOut = GzipCompressorOutputStream(out)

        IOUtils.copy(inputStream, gzOut)
        val fileIn: InputStream = Files.newInputStream(tmpPath)
        val size: Long = Files.size(tmpPath)
        s3AsyncClient.putObject(
            PutObjectRequest.builder().bucket("stacos").key(fileName)
                .metadata(
                    mapOf(
                        "Content-Type" to "application/x-gzip",
                        "Content-Length" to size.toString()
                    )
                ).build(),
            AsyncRequestBody.fromBytes(fileIn.readBytes())
        )
        logger.info { "Upload complete!" }
        logger.info { "File $path was created!" }
        logger.info { "File  $tmpPath was uploaded!" }

    }

}