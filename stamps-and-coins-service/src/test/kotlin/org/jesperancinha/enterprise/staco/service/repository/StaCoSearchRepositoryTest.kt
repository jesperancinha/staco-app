package org.jesperancinha.enterprise.staco.service.repository

import com.ninjasquad.springmockk.MockkBean
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.jesperancinha.enterprise.staco.service.containers.AbstractTestContainersIT.DockerPostgresDataInitializer
import org.jesperancinha.enterprise.staco.common.aws.StaCoDynamoDBRepository
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType.EUR
import org.jesperancinha.enterprise.staco.common.domain.ObjectType.COIN
import org.jesperancinha.enterprise.staco.common.domain.ObjectType.STAMP
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.service.domain.StaCo
import org.jesperancinha.enterprise.staco.service.domain.toData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*


@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@MockkBean(CacheManager::class, StaCoDynamoDBRepository::class)
@ContextConfiguration(initializers = [DockerPostgresDataInitializer::class])
internal class StaCoSearchRepositoryTest {

    private val staCo1: StaCo = StaCoDto(
        description = Description(value = "Queen of Power"),
        year = "1900",
        value = "10",
        currency = EUR,
        type = COIN,
        diameterMM = "10",
        internalDiameterMM = "0",
    ).toData.copy(stacoId = UUID.randomUUID())

    private val staCo2: StaCo = StaCoDto(
        description = Description(value = "Queen of Wealth"),
        year = "1900",
        value = "10",
        currency = EUR,
        type = STAMP,
        heightMM = "0",
        widthMM = "10"
    ).toData.copy(stacoId = UUID.randomUUID())

    @Autowired
    lateinit var staCoRepository: StaCoRepository

    @Autowired
    lateinit var staCoSearchRepository: StaCoSearchRepository

    @BeforeEach
    fun setUp(): Unit = runBlocking {
        staCoRepository.save(staCo1)
        staCoRepository.save(staCo2)
    }

    @Test
    fun testStaCoLike_whenSearchName_thenCallSearchName(): Unit = runBlocking {
        val searchItem = "%Wealth%"
        val entityRecords =
            staCoSearchRepository.findStaCosByDescriptionLike(
                searchItem,
                Pageable.unpaged()
            ).asFlow().toList()

        entityRecords.shouldHaveSize(1)
        val entity1 = entityRecords.elementAt(0)
        entity1 shouldBe staCo2.copy(version = 0)
    }
}
