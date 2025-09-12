package org.jesperancinha.enterprise.staco.cloud.service

import org.jesperancinha.enterprise.staco.cloud.domain.StaCo
import org.jesperancinha.enterprise.staco.cloud.repository.StaCoRepository
import org.springframework.stereotype.Service

@Service
class StaCoService(
    private val staCoRepository: StaCoRepository
) {
    fun save(staCo: StaCo) = staCoRepository.save(staCo)
    fun getById(dynId: String): StaCo? = staCoRepository.getById(dynId)
    fun findAll(): List<StaCo> = staCoRepository.findAll()
}

