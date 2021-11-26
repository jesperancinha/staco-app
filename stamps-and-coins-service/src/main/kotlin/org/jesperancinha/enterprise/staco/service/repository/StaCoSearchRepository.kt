package org.jesperancinha.enterprise.staco.service.repository

import org.jesperancinha.enterprise.staco.common.domain.CurrencyType
import org.jesperancinha.enterprise.staco.service.domain.StaCo
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

interface StaCoSearchRepository : ReactiveSortingRepository<StaCo, Long> {

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
    ): Flux<StaCo>

    suspend fun countStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
        description: String,
        year: String,
        value: String,
        currency: CurrencyType,
        diameterMM: String,
        internalDiameterMM: String,
        heightMM: String,
        widthMM: String
    ): Long
}

@Repository
interface StaCoRepository : CoroutineSortingRepository<StaCo, Long>
