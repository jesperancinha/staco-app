package org.jesperancinha.enterprise.staco.service.utils

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

class TestPostgresSQLContainer(imageName: String) : PostgreSQLContainer<TestPostgresSQLContainer>(imageName)

abstract class AbstractStaCoTest {
    init {
        postgresSQLContainer.start()
        localStackContainer.start()
    }

    companion object {
        @Container
        @JvmField
        val postgresSQLContainer: TestPostgresSQLContainer = TestPostgresSQLContainer("postgres:12")
            .withUsername("postgres")
            .withPassword("admin")
            .withDatabaseName("staco")
            .withInitScript("schema.sql")

        @Container
        @JvmField
        val localStackContainer: LocalStackContainer =
            LocalStackContainer(DockerImageName.parse("localstack/localstack:0.13.2"))
                .withServices(LocalStackContainer.Service.DYNAMODB)

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add(
                "spring.r2dbc.url"
            ) { "r2dbc:postgresql://postgres@localhost:${postgresSQLContainer.firstMappedPort}/staco" }
            registry.add(
                "aws.endpoint"
            ) { "http://localhost:${localStackContainer.firstMappedPort}" }
        }
    }
}