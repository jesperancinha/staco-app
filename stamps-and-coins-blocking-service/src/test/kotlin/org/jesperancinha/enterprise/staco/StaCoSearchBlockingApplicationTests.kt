package org.jesperancinha.enterprise.staco

import org.jesperancinha.enterprise.staco.blocking.containers.AbstractTestContainersIT.DockerPostgresDataInitializer
import org.jesperancinha.enterprise.staco.blocking.utils.AbstractStaCoTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = [DockerPostgresDataInitializer::class])
class StaCoSearchBlockingApplicationTests : AbstractStaCoTest() {

    @Test
    fun contextLoads() {
    }

}
