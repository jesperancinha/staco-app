package org.jesperancinha.enterprise.staco.jpa.utils

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container

class TestPostgresSQLContainer(imageName: String) : PostgreSQLContainer<TestPostgresSQLContainer>(imageName)

abstract class AbstractStaCoTest {
    companion object {
        @Container
        @JvmField
        val postgreSQLContainer: TestPostgresSQLContainer = TestPostgresSQLContainer("postgres:12")
            .withUsername("postgres")
            .withPassword("admin")
            .withDatabaseName("staco")


        init {
            postgreSQLContainer.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add(
                "spring.datasource.url"
            ) { "jdbc:postgresql://localhost:${postgreSQLContainer.firstMappedPort}/staco" }
        }
    }
}