package org.jesperancinha.enterprise.staco.ls.repo

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.ls.domain.StaCo
import org.springframework.context.annotation.Profile
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
import java.util.UUID
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct


@Repository
class StaCoRepository(
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) {
    private val logger = KotlinLogging.logger {}

    fun save(staCo: StaCo): StaCo =
        staCo.copy(dynId = UUID.randomUUID().toString()).also {
            val putItemRequest = PutItemRequest.builder()
                .tableName("stacos")
                .item(it.toEvent)
                .build()
            dynamoDbAsyncClient.putItem(putItemRequest).get()
            logger.info { "Saved item $staCo" }
        }

    @PostConstruct
    @Profile("sta")
    fun createTableIfNeeded() {
        val listTableResponse: CompletableFuture<ListTablesResponse> = dynamoDbAsyncClient.listTables()
        val createTableRequest = listTableResponse
            .thenCompose { response: ListTablesResponse ->
                val tableExist = response
                    .tableNames()
                    .contains("stacos")
                if (!tableExist) {
                    return@thenCompose createTable()
                } else {
                    return@thenCompose CompletableFuture.completedFuture<CreateTableResponse>(null)
                }
            }
        createTableRequest.get()
    }

    private fun createTable(): CompletableFuture<CreateTableResponse> {
        val keySchemaElement: KeySchemaElement = KeySchemaElement
            .builder()
            .attributeName("dynId")
            .keyType(KeyType.HASH)
            .build()
        val keySchemaElementD: KeySchemaElement = KeySchemaElement
            .builder()
            .attributeName("description")
            .keyType(KeyType.HASH)
            .build()
        val dynId: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("dynId")
            .attributeType(ScalarAttributeType.S)
            .build()
        val description: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("description")
            .attributeType(ScalarAttributeType.S)
            .build()
        val year: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("year")
            .attributeType(ScalarAttributeType.S)
            .build()
        val currency: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("currency")
            .attributeType(ScalarAttributeType.S)
            .build()
        val diameterMM: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("diameterMM")
            .attributeType(ScalarAttributeType.S)
            .build()
        val internalDiameterMM: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("internalDiameterMM")
            .attributeType(ScalarAttributeType.S)
            .build()
        val heightMM: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("heightMM")
            .attributeType(ScalarAttributeType.S)
            .build()
        val widthMM: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("widthMM")
            .attributeType(ScalarAttributeType.S)
            .build()
        val request: CreateTableRequest = CreateTableRequest.builder()
            .tableName("stacos")
            .keySchema(keySchemaElement)
            .attributeDefinitions(dynId)
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .build()
        return dynamoDbAsyncClient.createTable(request)
    }


}

private val StaCo.toEvent: Map<String, AttributeValue>
    get() = mapOf(
        "dynId" to AttributeValue.builder().s(dynId).build(),
//        "description" to AttributeValue.builder().s(description).build(),
//        "year" to AttributeValue.builder().s(year).build(),
//        "currency" to AttributeValue.builder().s(currency.toString()).build(),
//        "diameterMM" to AttributeValue.builder().s(diameterMM).build(),
//        "internalDiameterMM" to AttributeValue.builder().s(internalDiameterMM).build(),
//        "heightMM" to AttributeValue.builder().s(heightMM).build(),
//        "widthMM" to AttributeValue.builder().s(widthMM).build()
    )
