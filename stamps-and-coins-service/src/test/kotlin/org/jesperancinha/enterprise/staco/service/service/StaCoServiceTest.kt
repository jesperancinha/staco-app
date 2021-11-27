package org.jesperancinha.enterprise.staco.service.service

import io.kotest.common.runBlocking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.jesperancinha.enterprise.staco.service.repository.StaCoRepository
import org.jesperancinha.enterprise.staco.service.repository.StaCoSearchRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux

@ExtendWith(MockitoExtension::class)
internal class StaCoServiceTest {

    @InjectMocks
    lateinit var staCoService: StaCoService

    @Mock
    lateinit var staCoSearchRepository: StaCoSearchRepository

    @Mock
    lateinit var staCoRepository: StaCoRepository


    @BeforeEach
    fun setUp() {

        val searchItem = "%Ma%"
        `when`(
            staCoSearchRepository.findStaCosByDescriptionLike(
                searchItem,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "description"))
            )
        )
            .thenReturn(Flux.empty())
        CoroutineScope(IO).launch {
            `when`(staCoRepository.count()).thenReturn(10);
        }
    }

    @Test
    fun testPersonLike_whenSearchName_thenCallSearchName(): Unit = runBlocking {
        val searchItem = "%Ma%"
        staCoService.getAllInAllBySearchItem(
            searchItemValue = searchItem,
            pageEntities = 0,
            pageSizeEntities = 10,
            sortColumn = "description",
            order = "ASC"
        )
        verify(
            staCoSearchRepository,
            times(1)
        ).findStaCosByDescriptionLike(
            searchItem,
            PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "description"))
        )
    }
}