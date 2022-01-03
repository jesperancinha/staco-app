package org.jesperancinha.enterprise.staco

import org.jesperancinha.enterprise.staco.jpa.utils.AbstractStaCoTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class StaCoSearchBlockingApplicationTests : AbstractStaCoTest() {

    @Test
    fun contextLoads() {
    }

}
