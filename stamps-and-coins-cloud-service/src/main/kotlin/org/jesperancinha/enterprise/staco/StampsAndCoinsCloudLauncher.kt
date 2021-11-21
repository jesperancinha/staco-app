package org.jesperancinha.enterprise.staco

import org.jesperancinha.enterprise.staco.cloud.config.AwsProperties
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties(AwsProperties::class)
@SpringBootApplication
class StampsAndCoinsCloudLauncher

fun main(args: Array<String>) {
    SpringApplication.run(StampsAndCoinsCloudLauncher::class.java, *args)
}

