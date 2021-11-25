package org.jesperancinha.enterprise.staco.jpa.domain

import org.springframework.data.repository.kotlin.CoroutineSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface StaCoRepository : CoroutineSortingRepository<StaCo, Long>
