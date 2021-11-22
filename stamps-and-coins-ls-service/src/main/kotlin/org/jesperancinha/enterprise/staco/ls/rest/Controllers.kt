package org.jesperancinha.enterprise.staco.ls.rest

import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.ls.service.StacoDao
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.view.RedirectView
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.IOException


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

@Controller
internal class StaCoImageController(
    val s3AsyncClient: S3AsyncClient
) {
    @PostMapping("/staco/save")
    fun saveUser(
        @RequestParam("image") multipartFile: MultipartFile
    ): RedirectView? {
        val fileName: String = StringUtils.cleanPath(multipartFile.originalFilename)
        val putObjectRequest = PutObjectRequest.builder().bucket("staco-files").key(fileName).build()
        s3AsyncClient.putObject(putObjectRequest, AsyncRequestBody.fromBytes(multipartFile.bytes))
        return RedirectView("/staco", true)
    }
}