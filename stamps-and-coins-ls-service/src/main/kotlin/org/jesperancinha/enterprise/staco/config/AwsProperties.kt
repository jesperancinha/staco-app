package org.jesperancinha.enterprise.staco.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties("aws")
data class AwsProperties (
    val endpoint: URI,
    val region:String
)