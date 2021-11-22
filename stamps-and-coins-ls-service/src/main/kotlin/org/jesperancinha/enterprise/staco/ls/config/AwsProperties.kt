package org.jesperancinha.enterprise.staco.ls.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConfigurationProperties("aws")
data class AwsProperties @ConstructorBinding constructor(
    val endpoint: URI,
    val region: String,
    val accessKey: String,
    val secretKey: String
)