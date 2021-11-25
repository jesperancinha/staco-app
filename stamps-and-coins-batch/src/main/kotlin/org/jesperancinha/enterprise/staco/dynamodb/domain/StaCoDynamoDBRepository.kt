package org.jesperancinha.enterprise.staco.dynamodb.domain

import mu.KotlinLogging
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
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
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct


@Repository
class StaCoDynamoDBRepository(
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) {

    private val logger = KotlinLogging.logger {}

    fun save(staCoEvent: Map<String, AttributeValue>): Map<String, AttributeValue> {
        val putItemRequest = PutItemRequest.builder()
            .tableName(TABLE_STACOS)
            .item(staCoEvent)
            .build()
        dynamoDbAsyncClient.putItem(putItemRequest).get()
        return staCoEvent
    }

    /**
     * We take advantage of the fromFuture function provided by Mono.
     * We then get the items and flatMap them
     * The only thing we block is the retrieval of the items.
     * The function itself is not blocked.
     * Since Flux belongs to WebFlux and not coroutines, we don't use suspend in this case
     * Flux is itself already reactive, although in a completely different setting than flow.
     **/
    fun findAll(): Flux<MutableMap<String, AttributeValue>> {
        return Mono.fromFuture(
            dynamoDbAsyncClient.scan(ScanRequest.builder().tableName(TABLE_STACOS).build())
        ).map { it.items() }.flatMapIterable { it }
    }


    @PostConstruct
    fun createTableIfNeeded() {
        val listTableResponse: CompletableFuture<ListTablesResponse> = dynamoDbAsyncClient.listTables()
        val createTableRequest = listTableResponse
            .thenCompose { response: ListTablesResponse ->
                val tableExist = response
                    .tableNames()
                    .contains(TABLE_STACOS)
                if (!tableExist) {
                    logger.info { "Table $TABLE_STACOS does not exist! Creating..." }
                    return@thenCompose createTable()
                } else {
                    logger.info { "Table $TABLE_STACOS already exists" }
                    return@thenCompose CompletableFuture.completedFuture<CreateTableResponse>(null)
                }
            }
        createTableRequest.get()
    }

    private fun createTable(): CompletableFuture<CreateTableResponse> {
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
        val request: CreateTableRequest = CreateTableRequest.builder()
            .tableName(TABLE_STACOS)
            .keySchema(keySchemaElement)
            .attributeDefinitions(dynId)
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .build()
        return dynamoDbAsyncClient.createTable(request)
    }

    companion object {
        const val TABLE_STACOS = "stacos"
        const val ID = "id"
    }

}
