package org.jesperancinha.enterprise.staco.cloud.config

import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import java.net.URI

@Configuration
internal class StacoConfiguration {

    @Bean
    fun dynamoDbClient(staCoAwsProperties: StaCoAwsProperties): DynamoDbClient =
        DynamoDbClient.builder()
            .endpointOverride(URI.create(staCoAwsProperties.endpoint.toString()))
            .region(Region.of(staCoAwsProperties.region))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        staCoAwsProperties.accessKey,
                        staCoAwsProperties.secretKey
                    )
                )
            )
            .build()

    @Bean
    fun dynamoDbEnhancedClient(dynamoDbClient: DynamoDbClient): DynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build()
}