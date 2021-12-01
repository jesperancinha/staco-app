package org.jesperancinha.enterprise.staco.common.aws

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties.Companion.ID
import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties.Companion.STACOS_TABLE
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
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct


@Repository
class StaCoDynamoDBRepository(
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) {

    private val logger = KotlinLogging.logger {}

    fun save(staCoEvent: Map<String, AttributeValue>): Mono<PutItemResponse> {
        val putItemRequest = PutItemRequest.builder()
            .tableName(STACOS_TABLE)
            .item(staCoEvent)
            .build()
        dynamoDbAsyncClient.putItem(putItemRequest)
        return Mono.fromFuture { dynamoDbAsyncClient.putItem(putItemRequest) }
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
            dynamoDbAsyncClient.scan(ScanRequest.builder().tableName(STACOS_TABLE).build())
        ).map { it.items() }.flatMapIterable { it }
    }

    fun findByDescriptionLike(pageSize: Int): Flux<MutableMap<String, AttributeValue>> {
        return Mono.fromFuture(
            dynamoDbAsyncClient.scan(
                ScanRequest
                    .builder()
                    .limit(pageSize)
                    .tableName(STACOS_TABLE)
                    .exclusiveStartKey(null)
                    .build()
            )
        ).map {
            it.items()
        }.flatMapIterable { it }
    }

    fun findByDescriptionLike(
        pageSize: Int,
        pageNumber: Int
    ): Flux<MutableMap<String, AttributeValue>> {
        return Mono.fromFuture(
            dynamoDbAsyncClient.scan(
                ScanRequest
                    .builder()
                    .limit(pageSize * (pageNumber - 1))
                    .tableName(STACOS_TABLE)
                    .build()
            )
        ).flatMap {
            Mono.fromFuture(
                dynamoDbAsyncClient.scan(
                    ScanRequest
                        .builder()
                        .limit(pageSize)
                        .tableName(STACOS_TABLE)
                        .exclusiveStartKey(it.lastEvaluatedKey())
                        .build()
                )
            )
        }.map {
            it.items()
        }.flatMapIterable { it }

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
