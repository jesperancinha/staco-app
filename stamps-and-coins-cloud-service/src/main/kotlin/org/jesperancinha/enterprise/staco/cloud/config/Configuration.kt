package org.jesperancinha.enterprise.staco.cloud.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
internal class StacoConfiguration {

    @Bean
    fun dynamoDbMapperAsync(staCoAwsProperties: StaCoAwsProperties): DynamoDBMapper {
        return DynamoDBMapper(
            AmazonDynamoDBAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                    com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                        staCoAwsProperties.endpoint.toString(),
                        staCoAwsProperties.region
                    )
                )
                .withCredentials(
                    AWSStaticCredentialsProvider(
                        BasicAWSCredentials(
                            staCoAwsProperties.accessKey,
                            staCoAwsProperties.secretKey
                        )
                    )
                )
                .build()
        )
    }

}