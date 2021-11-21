package org.jesperancinha.enterprise.staco.ls.rest

import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.ls.service.StacoDao
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("staco")
internal class StaCoController(
    val stacoDao: StacoDao
) {

    @PostMapping("coin")
    fun sendCoin(@RequestBody staCoDto: StaCoDto) {
        stacoDao.saveCoin(staCoDto)
    }


    @PostMapping("stamp")
    fun sendStamp(@RequestBody staCoDto: StaCoDto) {
        stacoDao.saveStamp(staCoDto)
    }
}