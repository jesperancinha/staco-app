package org.jesperancinha.enterprise.staco.blocking.repository

import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.collections.shouldHaveSize
import org.jesperancinha.enterprise.staco.blocking.containers.AbstractTestContainersIT.DockerPostgresDataInitializer
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType.EUR
import org.jesperancinha.enterprise.staco.common.domain.ObjectType
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.jesperancinha.enterprise.staco.blocking.domain.StaCo
import org.jesperancinha.enterprise.staco.blocking.domain.toData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [DockerPostgresDataInitializer::class])
internal class StaCoRepositoryTest {

    val staCo1: StaCo = StaCoDto(
        description = Description(value = "Queen of Power"),
        year = "1900",
        value = "10",
        currency = EUR,
        type = ObjectType.COIN,
        diameterMM = "10",
        internalDiameterMM = "0",
        heightMM = null,
        widthMM = null
    ).toData

    val staCo2: StaCo = StaCoDto(
        description = Description(value = "Queen of Wealth"),
        year = "1900",
        value = "10",
        currency = EUR,
        type = ObjectType.STAMP,
        diameterMM = null,
        internalDiameterMM = null,
        heightMM = "0",
        widthMM = "10"
    ).toData

    @Autowired
    lateinit var staCoRepository: StaCoRepository

    @BeforeEach
    fun setUp() {
        staCoRepository.save(
            staCo1
        )
        staCoRepository.save(
            staCo2
        )
    }

    @Test
    fun testStaCoLike_whenSearchName_thenCallSearchName() {
        val searchItem = "%Stamp%"
        val entityRecords =
            staCoRepository.findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyEqualsOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
                searchItem,
                searchItem,
                searchItem,
                EUR,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                Pageable.unpaged()
            )

        entityRecords.shouldHaveSize(2)
        val entity = entityRecords.elementAt(0)
        entity shouldBeOneOf listOf(staCo2, staCo1)
    }
}