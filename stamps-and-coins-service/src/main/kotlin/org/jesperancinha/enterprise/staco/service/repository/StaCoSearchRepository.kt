package org.jesperancinha.enterprise.staco.service.repository

import org.jesperancinha.enterprise.staco.service.domain.StaCo
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

interface StaCoSearchRepository : ReactiveSortingRepository<StaCo, Long> {
    fun findStaCoBy(
        pageable: Pageable
    ): Flux<StaCo>

    fun findStaCosByDescriptionLike(
        description: String,
        pageable: Pageable
    ): Flux<StaCo>

     fun countStaCosByDescriptionLike(
        description: String
    ): Long
}

@Repository
interface StaCoRepository : CoroutineSortingRepository<StaCo, Long>
