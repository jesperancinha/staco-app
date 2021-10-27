package org.jesperancinha.enterprise.engine.repository

import org.assertj.core.api.Assertions.assertThat
import org.jesperancinha.enterprise.engine.domain.Currency
import org.jesperancinha.enterprise.engine.domain.StaCo
import org.jesperancinha.enterprise.engine.dto.StaCoDto
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
        currency = Currency.EUR,
        diameterMM = "10",
        internalDiameterMM = "0"
    ).toStaCo()

    val staCo2: StaCo = StaCoDto.createStamp(
        description = "Queen Stamp",
        year = "1900",
        value = "10",
        currency = Currency.EUR,
        widthMM = "10",
        heightMM = "0"
    ).toStaCo()

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
        assertThat(entityRecords).hasSize(1);
        val entity = entityRecords.elementAt(0)
        assertThat(entity).isEqualTo(staCo2);
        assertThat(entity).isNotEqualTo(staCo1);
    }
}