package org.jesperancinha.enterprise.staco.cloud.controller

import org.jesperancinha.enterprise.staco.cloud.domain.StaCo
import org.jesperancinha.enterprise.staco.cloud.service.StaCoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/staco")
class StaCoController(
    private val staCoService: StaCoService
) {
    @PostMapping
    fun save(@RequestBody staCo: StaCo): ResponseEntity<Void> {
        staCoService.save(staCo)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{dynId}")
    fun getById(@PathVariable dynId: String): ResponseEntity<StaCo> {
        val result = staCoService.getById(dynId)
        return if (result != null) ResponseEntity.ok(result) else ResponseEntity.notFound().build()
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<StaCo>> = ResponseEntity.ok(staCoService.findAll())
}

