package org.jesperancinha.enterprise.staco.service.repository

import com.ninjasquad.springmockk.MockkBean
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldHaveSize
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType.EUR
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.service.domain.StaCo
import org.jesperancinha.enterprise.staco.service.domain.toData
import org.jesperancinha.enterprise.staco.service.utils.AbstractStaCoTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@MockkBean(CacheManager::class)
internal class StaCoSearchRepositoryTest : AbstractStaCoTest() {

    private val staCo1: StaCo = StaCoDto.createCoin(
        description = "Queen Coin",
        year = "1900",
        value = "10",
        currency = EUR,
        diameterMM = "10",
        internalDiameterMM = "0"
    ).toData.copy(id = 1)

    private val staCo2: StaCo = StaCoDto.createStamp(
        description = "Queen Stamp",
        year = "1900",
        value = "10",
        currency = EUR,
        widthMM = "10",
        heightMM = "0"
    ).toData.copy(id = 2)


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
        val searchItem = "%Stamp%"
        val entityRecords =
            staCoSearchRepository.findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
                searchItem,
                searchItem,
                searchItem,
                EUR,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                Pageable.unpaged()
            ).asFlow().toList()

        entityRecords.shouldHaveSize(2)
        val entity1 = entityRecords.elementAt(0)
        entity1 shouldBeIn listOf(staCo2.copy(version = 0), staCo1.copy(version = 0))
        val entity2 = entityRecords.elementAt(1)
        entity2 shouldBeIn listOf(staCo2.copy(version = 0), staCo1.copy(version = 0))
    }
}
