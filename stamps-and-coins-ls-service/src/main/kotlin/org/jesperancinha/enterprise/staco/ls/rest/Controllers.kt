package org.jesperancinha.enterprise.staco.ls.rest

import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.ls.service.StacoDao
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID


@RestController
@RequestMapping("stacos")
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

@RestController
@RequestMapping("images")
internal class StaCoImageController(
    val s3AsyncClient: S3AsyncClient
) {
    @PostMapping("/save/{id}")
    fun saveUser(
        @RequestPart(value = "image", required = false) filePartMono: Mono<FilePart>,
        @PathVariable("id") uuid:UUID
    ): Mono<Void> {
        return filePartMono.flatMapMany {
            it.content()
        }.map {
            val putObjectRequest = PutObjectRequest.builder().bucket("staco").key("staco-image-$uuid.png").build()
            s3AsyncClient.putObject(
                putObjectRequest,
                AsyncRequestBody.fromBytes(it.asByteBuffer().array())
            )
        }.then()
    }
}
