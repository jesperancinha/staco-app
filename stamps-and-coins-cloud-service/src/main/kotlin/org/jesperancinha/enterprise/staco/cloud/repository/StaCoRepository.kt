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
    private val table: DynamoDbTable<StaCo> = enhancedClient.table("staco", TableSchema.fromBean(StaCo::class.java))

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

