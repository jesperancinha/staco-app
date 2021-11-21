package org.jesperancinha.enterprise.staco

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.ls.config.AwsProperties
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType.HASH
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import software.amazon.awssdk.services.s3.S3AsyncClient

@EnableConfigurationProperties(AwsProperties::class)
@SpringBootApplication
class StampsAndCoinsLocalStackLauncher(
    val s3AsyncClient: S3AsyncClient,
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) : ApplicationRunner {

    private val logger = KotlinLogging.logger {}

    override fun run(args: ApplicationArguments?) {
        s3AsyncClient.listBuckets().thenApply { response ->
            response.buckets().forEach {
                logger.info { it }
            }
        }
        val createTableRequest: CreateTableRequest = CreateTableRequest.builder()
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
        dynamoDbAsyncClient.apply {
            createTable(createTableRequest)
                .thenApply {
                    listTables().thenApply { response ->
                        response.tableNames().forEach { logger.info { it } }
                    }

                }
        }
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(StampsAndCoinsLocalStackLauncher::class.java, *args)
}

