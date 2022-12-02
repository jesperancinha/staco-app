package org.jesperancinha.enterprise.staco.blocking.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.jesperancinha.enterprise.staco.common.dto.ResponseDto
import org.jesperancinha.enterprise.staco.blocking.service.LoginService
import org.jesperancinha.enterprise.staco.blocking.service.StaCoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size


@RestController
@RequestMapping("api")
@Validated
class StaCoController(
    val staCoService: StaCoService,
    @Autowired(required = false)
    var loginService: LoginService
) {
    @GetMapping("staco/login")
    fun login(httpServletResponse: HttpServletResponse) {
        httpServletResponse.addHeader("Location", "http://localhost:4200/search")
        httpServletResponse.status = 302
    }

    @PostMapping("staco/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse) {
        loginService.logout(request)
    }

    @GetMapping("staco/all/{search}/{pageEntity}/{sizeEntities}/{sortColumn}/{order}")
    fun getAllInAllBySearchItem(
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