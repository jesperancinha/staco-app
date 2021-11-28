package org.jesperancinha.enterprise.staco.service.service

import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.jesperancinha.enterprise.staco.service.repository.StaCoRepository
import org.jesperancinha.enterprise.staco.service.repository.StaCoSearchRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux

@ExtendWith(MockKExtension::class)
internal class StaCoServiceTest {

    @InjectMockKs
    lateinit var staCoService: StaCoService

    @MockK
    lateinit var staCoSearchRepository: StaCoSearchRepository

    @MockK
    lateinit var staCoRepository: StaCoRepository


    @BeforeEach
    fun setUp() {

        val searchItem = "%Ma%"
        every {
            staCoSearchRepository.findStaCosByDescriptionLike(
                searchItem,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "description"))
            )
        } returns Flux.empty()

        coEvery {
            staCoSearchRepository.countStaCosByDescriptionLike(
                description = searchItem
            )
        } returns (10);
    }

    @Test
    fun testPersonLike_whenSearchName_thenCallSearchName(): Unit = runBlocking {
        val searchItem = "Ma"
        staCoService.getAllInAllBySearchItem(
            searchItemValue = searchItem,
            pageEntities = 0,
            pageSizeEntities = 10,
            sortColumn = "description",
            order = "ASC"
        )
        verify(exactly = 1) {
            staCoSearchRepository.findStaCosByDescriptionLike(
                "%$searchItem%",
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "description")))
        }
    }
}