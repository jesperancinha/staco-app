package org.jesperancinha.enterprise.staco.jpa.service

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType.EUR
import org.jesperancinha.enterprise.staco.common.dto.ResponseDto
import org.jesperancinha.enterprise.staco.jpa.domain.StaCo
import org.jesperancinha.enterprise.staco.jpa.domain.toDto
import org.jesperancinha.enterprise.staco.jpa.repository.StaCoRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class StaCoService(
    val staCoRepository: StaCoRepository
) {
    fun createStaco(staCo: StaCo): StaCo {
        return staCoRepository.save(staCo)
    }

    fun readStaco(id: Long): StaCo? = staCoRepository.findByIdOrNull(id)

    fun updateStaco(StaCo: StaCo): StaCo = staCoRepository.save(StaCo)

    fun deleteStaco(id: Long): Boolean {
        staCoRepository.deleteById(id)
        return true
    }

    @Cacheable("stacos-all")
    fun getAllInAllBySearchItem(
        searchItemValue: String,
        pageEntities: Int,
        pageSizeEntities: Int,
        sortColumn: String,
        order: String,
    ): ResponseDto {

        val searchEntities =
            staCoRepository.findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
                description = searchItemValue,
                year = searchItemValue,
                value = searchItemValue,
                currency = EUR,
                diameterMM = searchItemValue,
                internalDiameterMM = searchItemValue,
                heightMM = searchItemValue,
                widthMM = searchItemValue,
                pageable = PageRequest.of(
                    pageEntities,
                    pageSizeEntities,
                    Sort.by(Sort.Direction.valueOf(order.uppercase(Locale.getDefault())), sortColumn)
                )
            )
        val contentEntities = searchEntities.content

        return ResponseDto(
            staCoDtos = contentEntities.map { it.toDto },
            currentPage = pageEntities,
            totalRecords = searchEntities.numberOfElements.toLong(),
            totalPages = searchEntities.totalPages.toLong()
        )
    }
}