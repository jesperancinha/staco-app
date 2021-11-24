package org.jesperancinha.enterprise.staco.jpa.service

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.common.dto.ResponseDto
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.jpa.domain.StaCo
import org.jesperancinha.enterprise.staco.jpa.domain.toDto
import org.jesperancinha.enterprise.staco.jpa.repository.StaCoRepository
import org.jesperancinha.enterprise.staco.jpa.repository.StaCoSearchRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class StaCoService(
    val staCoRepository: StaCoRepository,
    val staCoSearchRepository: StaCoSearchRepository
) {
    suspend fun createStaco(staCo: StaCo): StaCoDto {
        return staCoRepository.save(staCo).toDto
    }

    suspend fun readStaco(id: Long): StaCo? = staCoRepository.findById(id)

    suspend fun updateStaco(staCo: StaCo): StaCoDto = staCoRepository.save(staCo).toDto

    suspend fun deleteStaco(id: Long): Boolean {
        staCoRepository.deleteById(id)
        return true
    }

    @Cacheable("stacos-all")
    suspend fun getAllInAllBySearchItem(
        searchItemValue: String,
        pageEntities: Int,
        pageSizeEntities: Int,
        sortColumn: String,
        order: String,
    ): ResponseDto {
        val searchEntities =
            staCoSearchRepository.findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
                description = searchItemValue,
                year = searchItemValue,
                value = searchItemValue,
                currency = CurrencyType.EUR,
                diameterMM = searchItemValue,
                internalDiameterMM = searchItemValue,
                heightMM = searchItemValue,
                widthMM = searchItemValue,
                pageable = PageRequest.of(
                    pageEntities,
                    pageSizeEntities,
                    Sort.by(Sort.Direction.valueOf(order.uppercase(Locale.getDefault())), sortColumn)
                )
            ).asFlow().toList().map { it.toDto }
        return ResponseDto(
            staCoDtos = searchEntities,
            currentPage = pageEntities,
            totalRecords = searchEntities.size.toLong(),
            totalPages = staCoRepository.count()
        )
    }

     fun getAll() =
         staCoRepository.findAll()
}