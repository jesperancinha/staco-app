package org.jesperancinha.demo.engine.repository

import org.jesperancinha.demo.engine.domain.StaCo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface StaCoRepository : PagingAndSortingRepository<StaCo, Long> {

    fun findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
        description: String,
        year: String,
        value: String,
        currency: String,
        diameterMM: String,
        internalDiameterMM: String,
        heightMM: String,
        widthMM: String,
        pageable: Pageable
    ): Page<StaCo>
}