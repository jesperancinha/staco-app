package org.jesperancinha.enterprise.staco.service.controller

import kotlinx.coroutines.flow.Flow
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.service.domain.StaCo
import org.jesperancinha.enterprise.staco.service.domain.toDto
import org.jesperancinha.enterprise.staco.service.service.StaCoService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@RestController
class RedirectController {
    @GetMapping("/")
    fun redirect() = "status" to "UP"
}

@RestController
@RequestMapping("stacos")
class StaCoController(
    val staCoService: StaCoService,
) {
    @Validated
    @GetMapping("search/{search}/{pageEntity}/{sizeEntities}/{sortColumn}/{order}")
    fun getAllBySearchItem(
        @PathVariable
        @Size(min = 1, max = 10)
        @Pattern(regexp = "[a-zA-Z0-9 ]*")
        search: String?,
        @PathVariable
        pageEntity: Int,
        @PathVariable
        sizeEntities: Int,
        @PathVariable
        sortColumn: String,
        @PathVariable
        order: String,
    ): Flux<StaCoDto> {
        if (search.isNullOrEmpty()) {
            return getUnfiltered(pageEntity, sizeEntities, sortColumn, order)
        }
        return staCoService.getAllBySearchItem(
            searchItemValue = search,
            pageEntities = pageEntity,
            pageSizeEntities = sizeEntities,
            sortColumn = sortColumn,
            order = order
        )
    }

    @Validated
    @GetMapping("count/search/{search}/{pageEntity}/{sizeEntities}/{sortColumn}/{order}")
    fun countBySearchItem(
        @PathVariable
        @Size(min = 1, max = 10)
        @Pattern(regexp = "[a-zA-Z0-9 ]*")
        search: String,
        @PathVariable
        pageEntity: Int,
        @PathVariable
        sizeEntities: Int,
        @PathVariable
        sortColumn: String,
        @PathVariable
        order: String,
    ): Mono<Long> {
        return staCoService.countAllBySearchItem(
            searchItemValue = search,
            pageEntities = pageEntity,
            pageSizeEntities = sizeEntities,
            sortColumn = sortColumn,
            order = order
        )

    }

    @GetMapping("all")
    fun getAll(): Flow<StaCo> =
        staCoService.getAll()


    @GetMapping("unfiltered/{pageEntity}/{sizeEntities}/{sortColumn}/{order}")
    fun getUnfiltered(
        @PathVariable
        pageEntity: Int,
        @PathVariable
        sizeEntities: Int,
        @PathVariable
        sortColumn: String,
        @PathVariable
        order: String,
    ): Flux<StaCoDto> = staCoService.getUnfiltered(
        pageEntities = pageEntity,
        pageSizeEntities = sizeEntities,
        sortColumn = sortColumn,
        order = order
    ).map { it.toDto }


    @GetMapping("count/unfiltered/{pageEntity}/{sizeEntities}/{sortColumn}/{order}")
    suspend fun countUnfiltered(
        @PathVariable
        pageEntity: Int,
        @PathVariable
        sizeEntities: Int,
        @PathVariable
        sortColumn: String,
        @PathVariable
        order: String,
    ) = staCoService.countUnfiltered(
            pageEntities = pageEntity,
            pageSizeEntities = sizeEntities,
            sortColumn = sortColumn,
            order = order)

}