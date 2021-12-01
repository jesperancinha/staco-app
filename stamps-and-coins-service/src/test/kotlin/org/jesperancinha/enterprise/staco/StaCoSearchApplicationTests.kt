package org.jesperancinha.enterprise.staco

import com.ninjasquad.springmockk.MockkBean
import org.jesperancinha.enterprise.staco.common.aws.StaCoDynamoDBRepository
import org.jesperancinha.enterprise.staco.service.utils.AbstractStaCoTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@MockkBean(StaCoDynamoDBRepository::class)
class StaCoSearchApplicationTests : AbstractStaCoTest() {

    @Test
    fun contextLoads() {
    }

}
