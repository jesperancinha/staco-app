package org.jesperancinha.enterprise.staco.dynamodb.domain

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.aws.AwsProperties.Companion.ID
import org.jesperancinha.enterprise.staco.common.aws.AwsProperties.Companion.STACOS_TABLE
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.BillingMode
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.CreateTableResponse
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct


@Repository
class StaCoDynamoDBRepository(
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) {

    private val logger = KotlinLogging.logger {}

    fun save(staCoEvent: Map<String, AttributeValue>): Map<String, AttributeValue> {
        val putItemRequest = PutItemRequest.builder()
            .tableName(STACOS_TABLE)
            .item(staCoEvent)
            .build()
        dynamoDbAsyncClient.putItem(putItemRequest).get()
        return staCoEvent
    }

    @PostConstruct
    fun createTableIfNotExists() {
        val listTableResponse: CompletableFuture<ListTablesResponse> = dynamoDbAsyncClient.listTables()
        val createTableRequest = listTableResponse
            .thenCompose { response: ListTablesResponse ->
                val tableExist = response
                    .tableNames()
                    .contains(STACOS_TABLE)
                if (!tableExist) {
                    logger.info { "Table $STACOS_TABLE does not exist! Creating..." }
                    return@thenCompose createStaCosTable()
                } else {
                    logger.info { "Table $STACOS_TABLE already exists" }
                    return@thenCompose CompletableFuture.completedFuture<CreateTableResponse>(null)
                }
            }
        createTableRequest.get()
    }

    private fun createStaCosTable(): CompletableFuture<CreateTableResponse> {
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
        return dynamoDbAsyncClient.createTable(
            CreateTableRequest.builder()
                .tableName(STACOS_TABLE)
                .keySchema(keySchemaElement)
                .attributeDefinitions(dynId)
                .billingMode(BillingMode.PAY_PER_REQUEST)
                .build()
        )
    }
}
