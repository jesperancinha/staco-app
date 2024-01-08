package org.jesperancinha.enterprise.staco.common.aws

import org.jesperancinha.enterprise.staco.common.aws.StaCosAwsClientsConfiguration.Companion.config
import org.jesperancinha.enterprise.staco.common.aws.StaCosAwsClientsConfiguration.Companion.staticCredentialsProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertySource
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.ssm.SsmAsyncClient
import software.amazon.awssdk.services.ssm.model.GetParameterRequest
import java.lang.System.getenv
import java.net.InetAddress
import java.net.URI

private val logger = object {
    fun info(text: String) = println(text)
}

@Configuration
@EnableConfigurationProperties(StaCoAwsProperties::class)
class StaCosAwsClientsConfiguration(
    @Value("\${aws.accessKeyId}")
    val accessKeyId: String,
    @Value("\${aws.secretKey}")
    val secretKey: String
) {

    val staticCredentialsProvider by lazy { staticCredentialsProvider(accessKeyId, secretKey) }

    @Bean
    fun dynamoDbClient(staCoAwsProperties: StaCoAwsProperties): DynamoDbAsyncClient =
        config(staCoAwsProperties, DynamoDbAsyncClient.builder(), staticCredentialsProvider)

    @Bean
    fun s3Client(staCoAwsProperties: StaCoAwsProperties): S3AsyncClient =
        config(staCoAwsProperties, S3AsyncClient.builder(), staticCredentialsProvider)

    @Bean
    fun ssmClient(staCoAwsProperties: StaCoAwsProperties): SsmAsyncClient =
        config(staCoAwsProperties, SsmAsyncClient.builder(), staticCredentialsProvider)

    companion object {
        fun <B : AwsClientBuilder<B, C>, C> config(
            staCoAwsProperties: StaCoAwsProperties,
            awsClientBuilder: AwsClientBuilder<B, C>,
            staticCredentialsProvider: StaticCredentialsProvider
        ): C = staCoAwsProperties.run {
            logger.info("${AwsClientBuilder::class.simpleName} initially configured on endpoint $endpoint")
            val newEndpoint =
                endpoint.run { URI.create("http://${InetAddress.getAllByName(host)[0].hostAddress}:${endpoint.port}") }
            logger.info("${AwsClientBuilder::class.simpleName} finally configured on endpoint $newEndpoint")
            awsClientBuilder.region(Region.of(region))
                .endpointOverride(newEndpoint)
                .credentialsProvider(staticCredentialsProvider as AwsCredentialsProvider)
                .build()
        }

        fun staticCredentialsProvider(accessKeyId: String, secretKey: String): StaticCredentialsProvider =
            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKeyId, secretKey))

    }
}


internal class ParameterStorePropertySource(name: String, ssmAsyncClient: SsmAsyncClient) :
    PropertySource<SsmAsyncClient>(name, ssmAsyncClient) {
    override fun getProperty(propertyName: String): Any? {
        logger.debug("Property $propertyName is not yet configured")
        if (propertyName.startsWith("/")) {
            logger.info("Fetching property ${propertyName.toPathPropertyName()}")
            val localstackValue = source.getParameter(
                GetParameterRequest.builder().name(propertyName.toPathPropertyName())
                    .build()
            )?.get()?.parameter()?.value()
            logger.info("Localstack param ${propertyName.toPathPropertyName()} created with value $localstackValue")
            return localstackValue
        }
        return null
    }
}

internal class ParameterStorePropertySourceEnvironmentPostProcessor : EnvironmentPostProcessor {
    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        val host = (getenv("STACO_AWS_LOCALSTACK_IP") ?: "localhost")
            .let { dns ->
                logger.info("Local stack DNS is $dns")
                InetAddress.getAllByName(dns)[0].hostAddress
            }
        val port = (getenv("STACO_AWS_LOCALSTACK_PORT") ?: "4566").apply { logger.info("Port is $this") }
        val protocol = (getenv("STACO_AWS_LOCALSTACK_PROTOCOL") ?: "http").apply { logger.info("Protocol is $this") }
        val accessKey = "test"
        val secretKey = "test"
        environment.propertySources
            .addLast(
                ParameterStorePropertySource(
                    "AWSParameterStorePropertySource",
                    config(
                        StaCoAwsProperties(
                            URI.create("$protocol://$host:$port"),
                            "eu-central-1",
                            accessKey,
                            secretKey
                        ).apply {
                            logger.info("Configured parameter properties: $this")
                        },
                        SsmAsyncClient.builder(),
                        staticCredentialsProvider(accessKey, secretKey)
                    )
                )
            )
    }

}

private fun String.toPathPropertyName(): String = "/config/StaCoLsService$this"
