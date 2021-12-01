package org.jesperancinha.enterprise.staco.ls.rest

import io.kotest.matchers.collections.shouldContain
import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties.Companion.ID
import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties.Companion.STACOS_TABLE
import org.jesperancinha.enterprise.staco.common.aws.StaCosAwsClientsConfiguration
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
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.BillingMode
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType

val localStackContainer: LocalStackContainer =
    LocalStackContainer(DockerImageName.parse("localstack/localstack:0.11.3"))
        .withServices(LocalStackContainer.Service.DYNAMODB)

@SpringBootTest
@ContextConfiguration(
    initializers = [StaCoControllerTest.Initializer::class],
    classes = [StaCosAwsClientsConfiguration::class],
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
        val keySchemaElement: KeySchemaElement = KeySchemaElement
            .builder()
            .attributeName(ID)
            .keyType(KeyType.HASH)
            .build()
        val dynId: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName(ID)
            .attributeType(ScalarAttributeType.S)
            .build()
        dynamoDbClient.createTable(
            CreateTableRequest.builder()
                .tableName(STACOS_TABLE)
                .keySchema(keySchemaElement)
                .attributeDefinitions(dynId)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build()
        )
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

