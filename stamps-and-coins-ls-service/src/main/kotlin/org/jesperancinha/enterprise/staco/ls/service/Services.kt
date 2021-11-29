package org.jesperancinha.enterprise.staco.ls.service

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.domain.toDto
import org.jesperancinha.enterprise.staco.common.domain.toEvent
import org.jesperancinha.enterprise.staco.common.dto.ResponseDto
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.ls.domain.StaCoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

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

    suspend fun getAllInAllBySearchItem(
        searchItemValue: String,
        pageNumber: Int,
        pageSize: Int,
        sortColumn: String,
        order: String
    ): ResponseDto {

        val stacos = getAllStacos(
            searchItemValue,
            pageNumber,
            pageSize
        ).asFlow().map { it.toDto }.toList()

        return ResponseDto(
            staCoDtos = stacos,
            currentPage = pageNumber,
        )
    }

    private fun getAllStacos(
        searchItemValue: String,
        pageNumber: Int,
        pageSize: Int,
    ): Flux<MutableMap<String, AttributeValue>> = if (pageNumber > 1) {
        staCoRepository.findByDescriptionLike(searchItemValue, pageSize, pageNumber)
    } else {
        staCoRepository.findByDescriptionLike(searchItemValue, pageSize)
    }
}
