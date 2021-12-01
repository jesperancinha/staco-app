package org.jesperancinha.enterprise.staco.service.service

import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.service.domain.StaCo
import org.jesperancinha.enterprise.staco.service.domain.toDto
import org.jesperancinha.enterprise.staco.service.repository.StaCoRepository
import org.jesperancinha.enterprise.staco.service.repository.StaCoSearchRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.Locale

@Service
class StaCoService(
    val staCoRepository: StaCoRepository,
    val staCoSearchRepository: StaCoSearchRepository
) {
    suspend fun createStaco(staCo: StaCo): StaCoDto {
        return staCoRepository.save(staCo).toDto
    }

    suspend fun readStaco(id: Long): StaCo? = staCoRepository.findById(id)

    suspend fun updateStaco(staCo: StaCo): StaCoDto = staCoRepository.save(staCo).toDto

    suspend fun deleteStaco(id: Long): Boolean {
        staCoRepository.deleteById(id)
        return true
    }

    @Cacheable("stacos-all")
    fun getAllBySearchItem(
        searchItemValue: String,
        pageEntities: Int,
        pageSizeEntities: Int,
        sortColumn: String,
        order: String,
    ) = staCoSearchRepository.findStaCosByDescriptionLike(
        description = "%$searchItemValue%",
        pageable = PageRequest.of(
            pageEntities,
            pageSizeEntities,
            Sort.by(Sort.Direction.valueOf(order.uppercase(Locale.getDefault())), sortColumn)
        )
    ).map { it.toDto }

    suspend fun countAllBySearchItem(
        searchItemValue: String,
        pageEntities: Int,
        pageSizeEntities: Int,
        sortColumn: String,
        order: String,
    ) = staCoSearchRepository
        .countStaCosByDescriptionLike(
            description = "%$searchItemValue%"
        )

    suspend fun countAll(
        searchItemValue: String,
        pageEntities: Int,
        pageSizeEntities: Int,
        sortColumn: String,
        order: String,
    ) = staCoRepository.count()


    fun getAll() =
        staCoRepository.findAll()

    fun getUnfiltered(
        pageEntities: Int,
        pageSizeEntities: Int,
        sortColumn: String,
        order: String,
    ) = staCoSearchRepository.findStaCoBy(
        pageable = PageRequest.of(
            pageEntities,
            pageSizeEntities,
            Sort.by(Sort.Direction.valueOf(order.uppercase(Locale.getDefault())), sortColumn)
        )
    )

    suspend fun countUnfiltered(
        pageEntities: Int,
        pageSizeEntities: Int,
        sortColumn: String,
        order: String,
    ) = staCoRepository.count()
}
