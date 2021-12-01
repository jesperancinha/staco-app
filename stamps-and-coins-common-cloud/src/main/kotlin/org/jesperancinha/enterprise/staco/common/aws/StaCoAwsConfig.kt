package org.jesperancinha.enterprise.staco.common.aws

import org.jesperancinha.enterprise.staco.common.aws.StaCosAwsClientsConfiguration.Companion.config
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
@EnableConfigurationProperties(StaCoAwsProperties::class)
class StaCosAwsClientsConfiguration {
    @Bean
    fun dynamoDbClient(staCoAwsProperties: StaCoAwsProperties): DynamoDbAsyncClient =
        config(staCoAwsProperties, DynamoDbAsyncClient.builder())

    @Bean
    fun s3Client(staCoAwsProperties: StaCoAwsProperties): S3AsyncClient =
        config(staCoAwsProperties, S3AsyncClient.builder())

    @Bean
    fun ssmClient(staCoAwsProperties: StaCoAwsProperties): SsmAsyncClient =
        config(staCoAwsProperties, SsmAsyncClient.builder())

    companion object {
        fun <B : AwsClientBuilder<B, C>, C> config(
            staCoAwsProperties: StaCoAwsProperties,
            awsClientBuilder: AwsClientBuilder<B, C>
        ): C {
            return awsClientBuilder.region(Region.of(staCoAwsProperties.region))
                .endpointOverride(staCoAwsProperties.endpoint)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build()
        }
    }
}

internal class ParameterStorePropertySource(name: String, ssmAsyncClient: SsmAsyncClient) :
    PropertySource<SsmAsyncClient>(name, ssmAsyncClient) {
    override fun getProperty(propertyName: String): Any? {
        logger.debug("Property $propertyName is not yet configured")
        if (propertyName.startsWith("/")) {
            val localstackValue = source.getParameter(
                GetParameterRequest.builder().name("/config/StaCoLsService/$propertyName")
                    .build()
            )?.get()?.parameter()?.value()
            logger.info("Localstack param /config/StaCoLsService$propertyName created with value $localstackValue")
            return localstackValue

        }
        return null
    }
}

internal class ParameterStorePropertySourceEnvironmentPostProcessor : EnvironmentPostProcessor {
    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        val host = System.getenv("STACO_AWS_LOCALSTACK_IP") ?: "localhost"
        val port = System.getenv("STACO_AWS_LOCALSTACK_PORT") ?: "4566"
        val protocol = System.getenv("STACO_AWS_LOCALSTACK_PROTOCOL") ?: "http"
        environment.propertySources
            .addLast(
                ParameterStorePropertySource(
                    "AWSParameterStorePropertySource",
                    config(
                        StaCoAwsProperties(
                            URI.create("$protocol://$host:$port"),
                            "eu-central-1",
                            "test",
                            "test"
                        ),
                        SsmAsyncClient.builder()
                    )
                )
            )
    }
}

