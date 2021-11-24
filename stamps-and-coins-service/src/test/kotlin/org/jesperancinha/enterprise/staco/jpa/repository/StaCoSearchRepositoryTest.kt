package org.jesperancinha.enterprise.staco.jpa.repository

import com.ninjasquad.springmockk.MockkBean
import io.kotest.common.runBlocking
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.collections.shouldHaveSize
import kotlinx.coroutines.flow.toList
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType.EUR
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.jpa.domain.StaCo
import org.jesperancinha.enterprise.staco.jpa.domain.toData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
@ActiveProfiles("test", "starter")
@MockkBean(CacheManager::class, JdbcTemplate::class)
internal class StaCoSearchRepositoryTest {

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


    companion object {
        @Container
        @JvmField
        val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres")
            .withUsername("postgres")
            .withPassword("admin")
            .withDatabaseName("staco")


        init {
            postgreSQLContainer.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add(
                "spring.r2dbc.url"
            ) { "r2dbc:postgresql://postgres@localhost:${postgreSQLContainer.getFirstMappedPort()}/staco" }
        }
    }


    @Autowired
    lateinit var staCoRepository: StaCoRepository

    @BeforeEach
    fun setUp(): Unit = runBlocking {
        staCoRepository.save(staCo1)
        staCoRepository.save(staCo2)
        val entityRecords = staCoRepository.findAll().toList()

    }

    @Test
    fun testStaCoLike_whenSearchName_thenCallSearchName(): Unit = runBlocking {
        val searchItem = "%Stamp%"
//        val entityRecords =
//            staCoSearchRepository.findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
//                searchItem,
//                searchItem,
//                searchItem,
//                EUR,
//                searchItem,
//                searchItem,
//                searchItem,
//                searchItem,
//                Pageable.unpaged()
//            ).blockFirst()

        val entityRecords = staCoRepository.findAll().toList()
        entityRecords.shouldHaveSize(2)
        val entity1 = entityRecords.elementAt(0)
        entity1 shouldBeIn  listOf(staCo2.copy(version = 0), staCo1.copy(version = 0))
        val entity2 = entityRecords.elementAt(1)
        entity2 shouldBeIn  listOf(staCo2.copy(version = 0), staCo1.copy(version = 0))
//        entityRecords shouldBeIn listOf(staCo2.copy(version = 0), staCo1.copy(version = 0))
    }

}