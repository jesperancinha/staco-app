package org.jesperancinha.enterprise.staco.service.utils

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

abstract class AbstractStaCoTest {
    init {
        postgreSQLContainer.start()
    }

    companion object {
        @Container
        @JvmField
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres")
            .withUsername("postgres")
            .withPassword("admin")
            .withDatabaseName("staco")
            .withInitScript("schema.sql")

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add(
                "spring.r2dbc.url"
            ) { "r2dbc:postgresql://postgres@localhost:${postgreSQLContainer.firstMappedPort}/staco" }
        }
    }
}