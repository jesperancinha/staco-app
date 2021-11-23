package org.jesperancinha.enterprise.staco.ls.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.ls.config.StaCoConfiguration.Companion.config
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertySource
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.ssm.SsmAsyncClient
import software.amazon.awssdk.services.ssm.model.GetParameterRequest
import java.net.URI


@Configuration
@EnableConfigurationProperties(AwsProperties::class)
internal class StaCoConfiguration {

    private val logger = KotlinLogging.logger {}

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
        return DynamoDBMapper(
            AmazonDynamoDBAsyncClientBuilder.standard()
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
                            awsProperties.secretKey
                        )
                    )
                )
                .build()
        )
    }


    companion object {
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

}

class ParameterStorePropertySource(name: String, ssmAsyncClient: SsmAsyncClient) :
    PropertySource<SsmAsyncClient>(name, ssmAsyncClient) {

    override fun getProperty(propertyName: String): Any? {
        logger.debug("Property $propertyName is not yet configured")
        if (propertyName.startsWith("/")) {
            return source.getParameter(
                GetParameterRequest.builder().name("/config/StaCoLsService/$propertyName")
                    .build()
            )?.get()?.parameter()?.value()

        }
        return null
    }
}

class ParameterStorePropertySourceEnvironmentPostProcessor : EnvironmentPostProcessor {

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        environment.propertySources
            .addLast(
                ParameterStorePropertySource(
                    "AWSParameterStorePropertySource",
                    config(
                        AwsProperties(URI.create("http://localhost:4566"), "eu-central-1", "test", "test"),
                        SsmAsyncClient.builder()
                    )
                )
            )
    }
}
