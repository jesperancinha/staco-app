package org.jesperancinha.enterprise.staco.blocking.domain

import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface StaCoRepository : CoroutineSortingRepository<StaCo, Long>
