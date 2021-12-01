package org.jesperancinha.enterprise.staco.ls.service

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.aws.StaCoDynamoDBRepository
import org.jesperancinha.enterprise.staco.common.aws.toDto
import org.jesperancinha.enterprise.staco.common.aws.toEvent
import org.jesperancinha.enterprise.staco.common.dto.ResponseDto
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

    suspend fun getAllInAllBySearchItem(
        pageNumber: Int,
        pageSize: Int,
    ): ResponseDto {

        val stacos = getAllStacos(
            pageNumber,
            pageSize
        ).asFlow().map { it.toDto }.toList()

        return ResponseDto(
            staCoDtos = stacos,
            currentPage = pageNumber,
        )
    }

    private fun getAllStacos(
        pageNumber: Int,
        pageSize: Int,
    ): Flux<MutableMap<String, AttributeValue>> = if (pageNumber > 1) {
        staCoDynamoDBRepository.findByDescriptionLike(pageSize, pageNumber)
    } else {
        staCoDynamoDBRepository.findByDescriptionLike(pageSize)
    }
}
