package org.jesperancinha.enterprise.staco.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.s3.S3AsyncClient


@Configuration
internal class StacoConfiguration {

    @Bean
    fun dynamoDbClient(dynamoProperties: DynamoProperties): DynamoDbAsyncClient =
        DynamoDbAsyncClient.builder()
            .region(Region.of(dynamoProperties.region))
            .endpointOverride(dynamoProperties.endpoint)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()

    @Bean
    fun s3Client(dynamoProperties: DynamoProperties): S3AsyncClient =
        S3AsyncClient.builder().region(Region.of(dynamoProperties.region))
            .endpointOverride(dynamoProperties.endpoint)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
}