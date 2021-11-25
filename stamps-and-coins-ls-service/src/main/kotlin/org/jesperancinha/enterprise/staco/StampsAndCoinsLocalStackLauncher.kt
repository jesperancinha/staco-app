package org.jesperancinha.enterprise.staco

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType.HASH
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import software.amazon.awssdk.services.s3.S3AsyncClient

@SpringBootApplication
class StampsAndCoinsLocalStackLauncher(
    val s3AsyncClient: S3AsyncClient,
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) : ApplicationRunner {

    @Value("\${aws.username:}")
    lateinit var username: String

    @Value("\${aws.password}")
    lateinit var password: String

    private val logger = KotlinLogging.logger {}

    override fun run(args: ApplicationArguments?) {
        s3AsyncClient.listBuckets().thenApply { response ->
            response.buckets().forEach {
                logger.info { it }
            }
        }
        val createTableRequest: CreateTableRequest = createTableRequest()
        dynamoDbAsyncClient.apply {
            createTable(createTableRequest)
                .thenApply {
                    listTables().thenApply { response ->
                        response.tableNames().forEach { logger.info { it } }
                    }

                }
        }
        logger.info { "Starting application with postgress user $username and password $password. Shhhh! Do not tell this to anyone! It comes from Localstack!" }
    }

    /**
     * This table isn't in use at the moment
     * But I'll leave it here for your understanding and reading.
     */
    companion object {
        fun createTableRequest(): CreateTableRequest = CreateTableRequest.builder()
            .tableName("staco")
            .keySchema(
                KeySchemaElement.builder()
                    .keyType(HASH)
                    .attributeName("id")
                    .build()
            )
            .attributeDefinitions(
                AttributeDefinition
                    .builder()
                    .attributeName("id")
                    .attributeType(ScalarAttributeType.S)
                    .build()
            )
            .provisionedThroughput(
                ProvisionedThroughput.builder()
                    .writeCapacityUnits(5L)
                    .readCapacityUnits(5L)
                    .build()
            )
            .build()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(StampsAndCoinsLocalStackLauncher::class.java, *args)
}

