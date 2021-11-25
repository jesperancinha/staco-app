package org.jesperancinha.enterprise.staco.ls.config

import org.jesperancinha.enterprise.staco.common.aws.AwsProperties
import org.jesperancinha.enterprise.staco.common.aws.AwsProperties.Companion.config
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.ssm.SsmAsyncClient


@Configuration
@EnableConfigurationProperties(AwsProperties::class)
internal class LsStaCoConfiguration {

    @Bean
    fun dynamoDbAsyncClient(awsProperties: AwsProperties): DynamoDbAsyncClient =
        config(awsProperties, DynamoDbAsyncClient.builder())

    @Bean
    fun s3AsyncClient(awsProperties: AwsProperties): S3AsyncClient =
        config(awsProperties, S3AsyncClient.builder())

    @Bean
    fun ssmAsyncClient(awsProperties: AwsProperties): SsmAsyncClient =
        config(awsProperties, SsmAsyncClient.builder())
}
