package org.jesperancinha.enterprise.staco.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties("dynamo")
class DynamoProperties (
    val endpoint: URI
)