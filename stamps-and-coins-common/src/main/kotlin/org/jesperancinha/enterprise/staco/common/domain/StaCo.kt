package org.jesperancinha.enterprise.staco.common.domain

import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.UUID

interface IStaCo {
    val stacoId: String?
    val description: String?
    var year: String?
    var value: String?
    var currency: CurrencyType?
    var type: ObjectType
    val diameterMM: String?
    val internalDiameterMM: String?
    val heightMM: String?
    val widthMM: String?
}

val StaCoDto.toEvent: Map<String, AttributeValue>
    get() = mapOf(
        "id" to AttributeValue.builder().s(id ?: UUID.randomUUID().toString()).build(),
        "description" to AttributeValue.builder().s(description?.value).build(),
        "year" to AttributeValue.builder().s(year).build(),
        "value" to AttributeValue.builder().s(value).build(),
        "currency" to AttributeValue.builder().s(currency.toString()).build(),
        "type" to AttributeValue.builder().s(type.toString()).build(),
        "diameterMM" to AttributeValue.builder().s(diameterMM ?: "").build(),
        "internalDiameterMM" to AttributeValue.builder().s(internalDiameterMM ?: "").build(),
        "heightMM" to AttributeValue.builder().s(heightMM ?: "").build(),
        "widthMM" to AttributeValue.builder().s(widthMM ?: "").build()
    )

val Map<String, AttributeValue>.toDto: StaCoDto
    get() = StaCoDto(
        description = Description(this["description"]?.s()),
        year = this["year"]?.s(),
        value = this["value"]?.s(),
        currency = this["currency"]?.let { CurrencyType.valueOf(it.s()) },
        type = this["type"]?.let { ObjectType.valueOf(it.s()) } ?: throw StaCoTypeNotSupportedException(),
        diameterMM = this["diameterMM"]?.s(),
        internalDiameterMM = this["internalDiameterMM"]?.s(),
        heightMM = this["heightMM"]?.s(),
        widthMM = this["widthMM"]?.s()
    )

class StaCoTypeNotSupportedException : RuntimeException()
