package org.jesperancinha.enterprise.staco.common.aws

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder
import software.amazon.awssdk.regions.Region
import java.net.URI

@ConfigurationProperties("aws")
data class AwsProperties @ConstructorBinding constructor(
    val endpoint: URI,
    val region: String,
    val accessKey: String,
    val secretKey: String
) {
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

        const val STACOS_BUCKET = "stacos"
        const val STACOS_TABLE = "stacos"
        const val IMAGES_BUCKET = "images"
        const val CREDENTIALS_BUCKET = "credentials"
        const val ID = "id"

    }
}