package org.jesperancinha.enterprise.staco.ls.repo

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue
import org.jesperancinha.enterprise.staco.ls.domain.StaCo
import org.springframework.stereotype.Repository

@Repository
class StaCoDao(
    val dynamoDBMapper: DynamoDBMapper
) {
    fun save(staCo: StaCo): StaCo {
        dynamoDBMapper.save(staCo)
        return staCo
    }

    fun getStaCoById(id: Long): StaCo? {
        return dynamoDBMapper.load(StaCo::class.java, id)
    }

    fun deleteStaCoById(id: Long) {
        val staCo = dynamoDBMapper.load(StaCo::class.java, id)
        dynamoDBMapper.delete(staCo)
    }

    fun update(id: Long, staCo: StaCo): StaCo {
        dynamoDBMapper.save(
            staCo,
            DynamoDBSaveExpression().withExpectedEntry(
                "id",
                ExpectedAttributeValue(
                    AttributeValue().withS(id.toString())
                )
            )
        )
        return staCo
    }
}