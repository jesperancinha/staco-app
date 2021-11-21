package org.jesperancinha.enterprise.staco.ls.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.ssm.SsmAsyncClient


@Configuration
internal class StaCoConfiguration {

    @Bean
    fun dynamoDbClient(awsProperties: AwsProperties): DynamoDbAsyncClient =
        config(awsProperties, DynamoDbAsyncClient.builder())

    @Bean
    fun s3Client(awsProperties: AwsProperties): S3AsyncClient =
        config(awsProperties, S3AsyncClient.builder())

    @Bean
    fun ssmClient(awsProperties: AwsProperties): SsmAsyncClient =
        config(awsProperties, SsmAsyncClient.builder())

    @Bean
    fun dynamoDbMapperAsync(awsProperties: AwsProperties): DynamoDBMapper? {
        return DynamoDBMapper(AmazonDynamoDBAsyncClientBuilder.standard()
            .withEndpointConfiguration(
                com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                    awsProperties.endpoint.toString(),
                    awsProperties.region
                )
            )
            .withCredentials(
                AWSStaticCredentialsProvider(
                    BasicAWSCredentials(
                        awsProperties.accessKey,
                        awsProperties.secretKet
                    )
                )
            )
            .build())
    }

    fun <B : AwsClientBuilder<B, C>, C> config(
        awsProperties: AwsProperties,
        awsClientBuilder: AwsClientBuilder<B, C>
    ): C {
        return awsClientBuilder.region(Region.of(awsProperties.region))
            .endpointOverride(awsProperties.endpoint)
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build()
    }
}