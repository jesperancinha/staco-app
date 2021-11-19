package org.jesperancinha.enterprise.staco.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Configuration
internal class StacoConfiguration {
    @Bean
    fun dynamoDbClient(): DynamoDbClient = DynamoDbClient.create()
}