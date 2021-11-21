package org.jesperancinha.enterprise.engine.repository

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.jesperancinha.enterprise.engine.domain.StaCo
import org.jesperancinha.enterprise.engine.domain.toData
import org.jesperancinha.enterprise.staco.common.domain.CurrencyEnum
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Pageable

@DataJpaTest
internal class StaCoRepositoryTest {

    val staCo1: StaCo = StaCoDto.createCoin(
        description = "Queen Coin",
        year = "1900",
        value = "10",
        currency = CurrencyEnum.EUR,
        diameterMM = "10",
        internalDiameterMM = "0"
    ).toData

    val staCo2: StaCo = StaCoDto.createStamp(
        description = "Queen Stamp",
        year = "1900",
        value = "10",
        currency = CurrencyEnum.EUR,
        widthMM = "10",
        heightMM = "0"
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
            staCoRepository.findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                Pageable.unpaged()
            )

        entityRecords.shouldHaveSize(1)
        val entity = entityRecords.elementAt(0)
        entity shouldBe staCo2
        entity shouldNotBe staCo1
    }
}