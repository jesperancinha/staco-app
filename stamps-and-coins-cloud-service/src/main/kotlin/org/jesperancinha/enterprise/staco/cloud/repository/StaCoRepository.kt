package org.jesperancinha.enterprise.staco.cloud.repository

import org.jesperancinha.enterprise.staco.cloud.domain.StaCo
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable

@Repository
class StaCoRepository(
    private val enhancedClient: DynamoDbEnhancedClient
) {
    private val table: DynamoDbTable<StaCo> =
        enhancedClient.table("staco", TableSchema.builder(StaCo::class.java)
            .newItemSupplier { StaCo() }
            .addAttribute(
                String::class.java,
                Attribute.builder<String>()
                    .name("dynId") // DynamoDB attribute name
                    .getter { it.dynId }
                    .setter { obj, value -> obj.dynId = value }
                    .tags(StaticAttributeTags.primaryPartitionKey())
                    .build()
            )
            .build())

    fun save(staCo: StaCo) {
        table.putItem(staCo)
    }

    fun getById(dynId: String): StaCo? {
        return table.getItem { r -> r.key { k -> k.partitionValue(dynId) } }
    }

    fun findAll(): List<StaCo> {
        return table.scan().items().toList()
    }
}

