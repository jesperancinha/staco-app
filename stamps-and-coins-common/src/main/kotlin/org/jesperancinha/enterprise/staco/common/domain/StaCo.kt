package org.jesperancinha.enterprise.staco.common.domain

import org.apache.commons.csv.CSVRecord
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

val CSVRecord.toDto: StaCoDto
    get() = StaCoDto(
        id = get("id"),
        description = Description(get("description")),
        year = get("year"),
        value = get("value"),
        currency = CurrencyType.valueOf(get("currency")),
        type = ObjectType.valueOf(get("type")),
        diameterMM = get("diameterMM"),
        internalDiameterMM = get("internalDiameterMM"),
        heightMM = get("heightMM"),
        widthMM = get("widthMM")
    )

val CSVRecord.toEvent: Map<String, AttributeValue>
    get() = mapOf(
        "id" to AttributeValue.builder().s(get("id") ?: UUID.randomUUID().toString()).build(),
        "description" to AttributeValue.builder().s(get("description")).build(),
        "year" to AttributeValue.builder().s(get("year")).build(),
        "value" to AttributeValue.builder().s(get("value")).build(),
        "currency" to AttributeValue.builder().s(get("currency")).build(),
        "type" to AttributeValue.builder().s(get("type")).build(),
        "diameterMM" to AttributeValue.builder().s(get("diameterMM") ?: "").build(),
        "internalDiameterMM" to AttributeValue.builder().s(get("internalDiameterMM") ?: "").build(),
        "heightMM" to AttributeValue.builder().s(get("heightMM") ?: "").build(),
        "widthMM" to AttributeValue.builder().s(get("widthMM") ?: "").build()
    )

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
