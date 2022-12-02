package org.jesperancinha.enterprise.staco.blocking.containers

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
            .withReuse(true)
    }

    init {
        postgreSQLContainer.start()
    }

    class DockerPostgresDataInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        var jdbcUrl = "spring.datasource.url=" + postgreSQLContainer.jdbcUrl
        var username = "spring.datasource.username=" + postgreSQLContainer.username
        var password = "spring.datasource.password=" + postgreSQLContainer.password
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertySourceUtils
                .addInlinedPropertiesToEnvironment(applicationContext, jdbcUrl, username, password)
        }
    }
}