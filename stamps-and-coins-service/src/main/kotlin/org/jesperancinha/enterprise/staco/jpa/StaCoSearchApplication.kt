package org.jesperancinha.enterprise.staco.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class StaCoSearchApplication

fun main(args: Array<String>) {
    runApplication<StaCoSearchApplication>(*args)
}