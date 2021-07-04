package org.jesperancinha.demo.engine.service

import org.jesperancinha.demo.engine.domain.StaCo
import org.jesperancinha.demo.engine.dto.ResponseDto
import org.jesperancinha.demo.engine.dto.StaCoDto
import org.jesperancinha.demo.engine.repository.StaCoRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StaCoService(
    val staCoRepository: StaCoRepository
) {
    fun createStaco(staCo: StaCo): StaCo {
        return staCoRepository.save(staCo);
    }

    fun readStaco(id: Long): StaCo? {
        return staCoRepository.findByIdOrNull(id)
    }

    fun updateStaco(StaCo: StaCo): StaCo {
        return staCoRepository.save(StaCo)
    }

    fun deleteStaco(id: Long): Boolean {
        staCoRepository.deleteById(id);
        return true;
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
                currency = searchItemValue,
                diameterMM = searchItemValue,
                internalDiameterMM = searchItemValue,
                heightMM = searchItemValue,
                widthMM = searchItemValue,
                pageable = PageRequest.of(
                    pageEntities,
                    pageSizeEntities,
                    Sort.by(Sort.Direction.valueOf(order.toUpperCase()), sortColumn)
                )
            )
        val contentEntities = searchEntities.content

        return ResponseDto(
            staCoDtos = contentEntities.map { StaCoDto(it) },
            currentPage = pageEntities,
            totalRecords = searchEntities.numberOfElements,
            totalPages = searchEntities.totalPages
        )
    }
}