package org.jesperancinha.enterprise.staco.ls.service

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.aws.StaCoDynamoDBRepository
import org.jesperancinha.enterprise.staco.common.aws.toDto
import org.jesperancinha.enterprise.staco.common.aws.toEvent
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

@Service
internal class StacoDao(
    val staCoDynamoDBRepository: StaCoDynamoDBRepository
) {
    private val logger = KotlinLogging.logger {}

    fun saveCoin(staCoDto: StaCoDto): Mono<StaCoDto> = staCoDynamoDBRepository.save(staCoDto.toEvent)
        .map { it.attributes().toDto.also { logger.debug { "Coin $staCoDto saved in dynamoDb" } } }

    fun saveStamp(staCoDto: StaCoDto): Mono<StaCoDto> = staCoDynamoDBRepository.save(staCoDto.toEvent)
        .map { it.attributes().toDto.also { logger.debug { "Stamp $staCoDto saved in dynamoDb" } } }

    fun getAll(): Flux<StaCoDto> = staCoDynamoDBRepository.findAll().map { it.toDto }

    fun getAllByPageNumberAndSize(
        pageNumber: Int,
        pageSize: Int,
    ) = getByPageNumberAndPageSize(
        pageNumber,
        pageSize
    ).map { it.toDto }

    private fun getByPageNumberAndPageSize(
        pageNumber: Int,
        pageSize: Int,
    ): Flux<MutableMap<String, AttributeValue>> = if (pageNumber > 1) {
        staCoDynamoDBRepository.findByPageNumberAndPageSize(pageSize, pageNumber)
    } else {
        staCoDynamoDBRepository.findByPageNumberAndPageSize(pageSize)
    }
}
