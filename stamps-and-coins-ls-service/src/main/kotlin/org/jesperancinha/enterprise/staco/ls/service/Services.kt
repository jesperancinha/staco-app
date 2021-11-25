package org.jesperancinha.enterprise.staco.ls.service

import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.common.domain.toDto
import org.jesperancinha.enterprise.staco.common.domain.toEvent
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.ls.domain.StaCoRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

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
