package org.jesperancinha.enterprise.staco.jpa.repository

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.jpa.domain.StaCo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface StaCoRepository : PagingAndSortingRepository<StaCo, Long>, CrudRepository<StaCo, Long> {

    fun findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
        description: String,
        year: String,
        value: String,
        currency: CurrencyType,
        diameterMM: String,
        internalDiameterMM: String,
        heightMM: String,
        widthMM: String,
        pageable: Pageable
    ): Page<StaCo>
}