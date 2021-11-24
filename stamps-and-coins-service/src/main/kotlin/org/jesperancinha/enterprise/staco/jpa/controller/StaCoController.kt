package org.jesperancinha.enterprise.staco.jpa.controller

import org.jesperancinha.enterprise.staco.common.dto.ResponseDto
import org.jesperancinha.enterprise.staco.jpa.service.StaCoService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size


@RestController
@RequestMapping("api")
@Validated
class StaCoController(
    val staCoService: StaCoService,
) {
    @GetMapping("staco/login")
    suspend fun login(httpServletResponse: HttpServletResponse) {
        httpServletResponse.addHeader("Location", "http://localhost:4200/search")
        httpServletResponse.status = 302
    }

    @GetMapping("staco/all/{search}/{pageEntity}/{sizeEntities}/{sortColumn}/{order}")
    suspend fun getAllInAllBySearchItem(
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
    ): ResponseDto {
        return staCoService.getAllInAllBySearchItem(
            searchItemValue = "%".plus(search).plus("%"),
            pageEntities = pageEntity,
            pageSizeEntities = sizeEntities,
            sortColumn = sortColumn,
            order = order
        )

    }
}