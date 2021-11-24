package org.jesperancinha.enterprise.staco.jpa.utils

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

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add(
                "spring.datasource.url"
            ) { "jdbc:postgresql://localhost:${postgreSQLContainer.getFirstMappedPort()}/staco" }
        }
    }
}