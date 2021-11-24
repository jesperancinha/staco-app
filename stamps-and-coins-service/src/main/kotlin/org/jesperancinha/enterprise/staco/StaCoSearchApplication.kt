package org.jesperancinha.enterprise.staco

import io.r2dbc.spi.ConnectionFactory
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@SpringBootApplication
@EnableCaching
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class StaCoSearchApplication(
    @Value("\${spring.r2dbc.username}")
    private val postgresUsername: String
) {

    private val logger = KotlinLogging.logger {}

    @Bean
    @Profile("starter")
    fun initializer(@Qualifier("connectionFactory") connectionFactory: ConnectionFactory): ConnectionFactoryInitializer? {
        logger.info("Using postgres user: $postgresUsername")
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory)
        val resource = ResourceDatabasePopulator(ClassPathResource("schema.sql"))
        initializer.setDatabasePopulator(resource)
        return initializer
    }

}

fun main(args: Array<String>) {
    runApplication<StaCoSearchApplication>(*args)
}