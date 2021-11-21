package org.jesperancinha.enterprise.staco.jpa.service

import org.jesperancinha.enterprise.engine.repository.StaCoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@ExtendWith(MockitoExtension::class)
internal class StaCoServiceTest {

    @InjectMocks
    lateinit var staCoService: StaCoService

    @Mock
    lateinit var staCoRepository: StaCoRepository


    @BeforeEach
    fun setUp() {

        val searchItem = "%Ma%"
        `when`(
            staCoRepository.findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                searchItem,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "description"))
            )
        )
            .thenReturn(Page.empty())
    }

    @Test
    fun testPersonLike_whenSearchName_thenCallSearchName() {
        val searchItem = "%Ma%"
        staCoService.getAllInAllBySearchItem(
            searchItemValue = searchItem,
            pageEntities = 0,
            pageSizeEntities = 10,
            sortColumn = "description",
            order = "ASC"
        )
        verify(
            staCoRepository,
            times(1)
        ).findStaCosByDescriptionLikeOrYearLikeOrValueLikeOrCurrencyLikeOrDiameterMMLikeOrInternalDiameterMMLikeOrHeightMMLikeOrWidthMMLike(
            searchItem,
            searchItem,
            searchItem,
            searchItem,
            searchItem,
            searchItem,
            searchItem,
            searchItem,
            PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "description"))
        )
    }
}