package org.jesperancinha.enterprise.staco.ls.rest

import io.kotest.matchers.collections.shouldContain
import org.jesperancinha.enterprise.staco.ls.config.LsStaCoConfiguration
import org.jesperancinha.enterprise.staco.ls.domain.StaCoRepository.Companion.createTableRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

val localStackContainer: LocalStackContainer =
    LocalStackContainer(DockerImageName.parse("localstack/localstack:0.11.3"))
        .withServices(LocalStackContainer.Service.DYNAMODB)

@SpringBootTest
@ContextConfiguration(
    initializers = [StaCoControllerTest.Initializer::class],
    classes = [LsStaCoConfiguration::class],
)
@TestPropertySource(locations = ["classpath:application.properties"])
internal class StaCoControllerTest(
    @Autowired
    val dynamoDbClient: DynamoDbAsyncClient
) {

    @Test
    fun sendCoin() {
    }

    @Test
    fun sendStamp() {
    }

    @Test
    fun getStacoDao() {
    }

    @Test
    fun `should create table`() {
        dynamoDbClient.createTable(createTableRequest())
        dynamoDbClient.apply {
            listTables().thenApply {
                it.tableNames().shouldContain("staco")
            }
        }
    }

    internal object Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            localStackContainer.start()
            val testPropertyValues = TestPropertyValues.of(
                "aws.endpoint="
                        + localStackContainer.getEndpointConfiguration(LocalStackContainer.Service.DYNAMODB)
                    .serviceEndpoint
            )
            testPropertyValues.applyTo(applicationContext)
        }
    }
}

