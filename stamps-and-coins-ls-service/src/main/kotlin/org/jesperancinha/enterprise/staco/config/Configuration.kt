package org.jesperancinha.enterprise.staco.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class StacoConfiguration {
    @Bean
    fun dynamoDbClient(): AmazonDynamoDBAsyncClient = AmazonDynamoDBAsyncClient()
}