package org.jesperancinha.enterprise.staco.ls.service

import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.ls.domain.toData
import org.jesperancinha.enterprise.staco.ls.domain.toDto
import org.jesperancinha.enterprise.staco.ls.repo.StaCoRepository
import org.springframework.stereotype.Service

@Service
internal class StacoDao(
   val staCoRepository: StaCoRepository
) {
    fun saveCoin(staCoDto: StaCoDto) {
        staCoRepository.save(staCoDto.toData).toDto
    }
    fun saveStamp(staCoDto: StaCoDto) {
        staCoRepository.save(staCoDto.toData).toDto
    }
}