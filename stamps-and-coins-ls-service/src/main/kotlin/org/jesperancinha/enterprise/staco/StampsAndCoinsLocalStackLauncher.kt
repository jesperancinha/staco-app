package org.jesperancinha.enterprise.staco

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
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
        dynamoDbAsyncClient.apply {
            listTables().thenApply { response ->
                response.tableNames().forEach {
                    logger.info { it }
                }
            }
        }
        logger.info { "Starting application with postgress user $username and password $password. Shhhh! Do not tell this to anyone! It comes from Localstack!" }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(StampsAndCoinsLocalStackLauncher::class.java, *args)
}

