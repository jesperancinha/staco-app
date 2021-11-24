package org.jesperancinha.enterprise.staco

import org.jesperancinha.enterprise.staco.service.utils.AbstractStaCoTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class StaCoSearchApplicationTests : AbstractStaCoTest() {

    @Test
    fun contextLoads() {
    }

}
