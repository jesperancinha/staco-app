package org.jesperancinha.enterprise.staco.common.aws

import org.apache.commons.csv.CSVRecord
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.domain.ObjectType
import org.jesperancinha.enterprise.staco.common.domain.StaCoTypeNotSupportedException
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.net.URI
import java.util.UUID

@ConfigurationProperties("aws")
data class StaCoAwsProperties @ConstructorBinding constructor(
    val endpoint: URI,
    val region: String,
    val accessKey: String,
    val secretKey: String
) {
    companion object {
        const val STACOS_BUCKET = "stacos"
        const val STACOS_TABLE = "stacos"
        const val IMAGES_BUCKET = "images"
        const val ID = "id"
    }
}

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
        currency = this["currency"]?.let { CurrencyType.valueOf(it.s()) } ?: CurrencyType.EUR,
        type = this["type"]?.let { ObjectType.valueOf(it.s()) } ?: throw StaCoTypeNotSupportedException(),
        diameterMM = this["diameterMM"]?.s(),
        internalDiameterMM = this["internalDiameterMM"]?.s(),
        heightMM = this["heightMM"]?.s(),
        widthMM = this["widthMM"]?.s()
    )
