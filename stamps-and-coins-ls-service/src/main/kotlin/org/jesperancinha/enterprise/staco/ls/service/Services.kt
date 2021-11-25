package org.jesperancinha.enterprise.staco.ls.service

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.ls.repo.StaCoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import java.util.UUID

@Service
internal class StacoDao(
    val staCoRepository: StaCoRepository
) {
    private val logger = KotlinLogging.logger {}

    suspend fun saveCoin(staCoDto: StaCoDto) = run {
        staCoRepository.save(staCoDto.toEvent).toDto
    }.also { logger.debug { "Coin $staCoDto saved in dynamoDb" } }

    suspend fun saveStamp(staCoDto: StaCoDto) = run {
        staCoRepository.save(staCoDto.toEvent).toDto
    }.also { logger.debug { "Stamp $staCoDto saved in dynamoDb" } }


    fun getAll(): Flux<StaCoDto> = staCoRepository.findAll().map { it.toDto }
}


private val StaCoDto.toEvent: Map<String, AttributeValue>
    get() = mapOf(
        "id" to AttributeValue.builder().s(UUID.randomUUID().toString()).build(),
        "description" to AttributeValue.builder().s(description?.value).build(),
        "year" to AttributeValue.builder().s(year).build(),
        "currency" to AttributeValue.builder().s(currency.toString()).build(),
        "diameterMM" to AttributeValue.builder().s(diameterMM ?: "").build(),
        "internalDiameterMM" to AttributeValue.builder().s(internalDiameterMM ?: "").build(),
        "heightMM" to AttributeValue.builder().s(heightMM ?: "").build(),
        "widthMM" to AttributeValue.builder().s(widthMM ?: "").build()
    )

private val Map<String, AttributeValue>.toDto: StaCoDto
    get() = StaCoDto(
        description = Description(this["description"]?.s()),
        year = this["year"]?.s(),
        currency = this["currency"]?.let { CurrencyType.valueOf(it.s()) },
        diameterMM = this["diameterMM"]?.s(),
        internalDiameterMM = this["internalDiameterMM"]?.s(),
        heightMM = this["heightMM"]?.s(),
        widthMM = this["widthMM"]?.s()
    )
