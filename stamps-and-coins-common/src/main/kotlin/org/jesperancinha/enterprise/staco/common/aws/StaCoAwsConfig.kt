package org.jesperancinha.enterprise.staco.common.aws

import org.jesperancinha.enterprise.staco.common.aws.AwsProperties.Companion.config
import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertySource
import software.amazon.awssdk.services.ssm.SsmAsyncClient
import software.amazon.awssdk.services.ssm.model.GetParameterRequest
import java.net.URI

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
                        AwsProperties(
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

