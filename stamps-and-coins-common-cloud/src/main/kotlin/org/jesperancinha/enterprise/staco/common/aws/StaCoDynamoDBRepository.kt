package org.jesperancinha.enterprise.staco.common.aws

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties.Companion.ID
import org.jesperancinha.enterprise.staco.common.aws.StaCoAwsProperties.Companion.STACOS_TABLE
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import javax.annotation.PostConstruct


@Repository
class StaCoDynamoDBRepository(
    val dynamoDbAsyncClient: DynamoDbAsyncClient
) {

    private val logger = KotlinLogging.logger {}

    fun save(staCoEvent: Map<String, AttributeValue>): Mono<PutItemResponse> =
        PutItemRequest.builder()
            .tableName(STACOS_TABLE)
            .item(staCoEvent)
            .build().let {
                dynamoDbAsyncClient.putItem(it)
                Mono.fromFuture { dynamoDbAsyncClient.putItem(it) }
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

    fun findByPageNumberAndPageSize(pageSize: Int): Flux<MutableMap<String, AttributeValue>> {
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

    fun findByPageNumberAndPageSize(
        pageSize: Int,
        pageNumber: Int
    ): Flux<MutableMap<String, AttributeValue>> = Mono.fromFuture(
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
        try {
            createTableRequest.get()
        } catch (ex: ExecutionException) {
            logger.info { "Table $STACOS_TABLE seems to already exist. Error  message is ${ex.toString()} " }
        }
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
