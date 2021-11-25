package org.jesperancinha.enterprise.staco.ls.repo

import org.springframework.context.annotation.Profile
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
class StaCoRepository(
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) {

    fun save(staCoEvent: Map<String, AttributeValue>): Map<String, AttributeValue> {
        val putItemRequest = PutItemRequest.builder()
            .tableName("stacos")
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
            dynamoDbAsyncClient.scan(ScanRequest.builder().tableName("stacos").build())
        ).map { it.items() }.flatMapIterable { it }
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
            .attributeName("id")
            .keyType(KeyType.HASH)
            .build()
        val dynId: AttributeDefinition = AttributeDefinition
            .builder()
            .attributeName("id")
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
