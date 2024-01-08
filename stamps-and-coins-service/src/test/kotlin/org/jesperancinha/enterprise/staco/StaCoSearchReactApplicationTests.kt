package org.jesperancinha.enterprise.staco

import com.ninjasquad.springmockk.MockkBean
import org.jesperancinha.enterprise.staco.common.aws.StaCoDynamoDBRepository
import org.jesperancinha.enterprise.staco.service.utils.AbstractStaCoTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@MockkBean(StaCoDynamoDBRepository::class)
class StaCoSearchReactApplicationTests : AbstractStaCoTest() {

    @Test
    fun contextLoads() {
    }

}
