package org.jesperancinha.enterprise.staco.service.containers

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer

class TestPostgresSQLContainer(imageName: String) : PostgreSQLContainer<TestPostgresSQLContainer>(imageName)

object AbstractTestContainersIT {
    val postgreSQLContainer: TestPostgresSQLContainer by lazy {
        TestPostgresSQLContainer("postgres:15")
            .withUsername("postgres")
            .withPassword("admin")
            .withDatabaseName("staco")
            .withInitScript("schema.sql")
            .withReuse(true)
    }

    init {
        postgreSQLContainer.start()
    }

    class DockerPostgresDataInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        var jdbcUrl = "spring.r2dbc.url=r2dbc:postgresql://postgres@${postgreSQLContainer.host}:${postgreSQLContainer.firstMappedPort}/staco"
        var username = "spring.r2dbc.username=" + postgreSQLContainer.username
        var password = "spring.r2dbc.password=" + postgreSQLContainer.password
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertySourceUtils
                .addInlinedPropertiesToEnvironment(applicationContext, jdbcUrl, username, password)
        }
    }
}