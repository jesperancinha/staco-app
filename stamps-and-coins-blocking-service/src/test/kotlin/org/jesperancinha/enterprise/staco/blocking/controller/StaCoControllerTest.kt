package org.jesperancinha.enterprise.staco.blocking.controller

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.validation.ConstraintViolationException
import org.assertj.core.api.Assertions.assertThat
import org.jesperancinha.enterprise.staco.blocking.service.LoginService
import org.jesperancinha.enterprise.staco.blocking.service.StaCoService
import org.jesperancinha.enterprise.staco.common.domain.CurrencyType.EUR
import org.jesperancinha.enterprise.staco.common.domain.ObjectType
import org.jesperancinha.enterprise.staco.common.dto.Description
import org.jesperancinha.enterprise.staco.common.dto.ResponseDto
import org.jesperancinha.enterprise.staco.common.dto.StaCoDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.sql.DataSource


@WebMvcTest(controllers = [StaCoController::class])
@ActiveProfiles("test")
@MockBean(classes = [DataSource::class, LoginService::class])
internal class StaCoControllerTest {

    private lateinit var mockMvc: MockMvc

    private val testdata = ResponseDto(
        staCoDtos = arrayListOf(
            StaCoDto(
                description = Description(value = "Queen of Power"),
                year = "1900",
                value = "10",
                currency = EUR,
                type = ObjectType.COIN,
                diameterMM = "10",
                internalDiameterMM = "0",
                heightMM = null,
                widthMM = null
            ),
            StaCoDto(
                description = Description(value = "Queen Stammp"),
                year = "1900",
                value = "10",
                currency = EUR,
                type = ObjectType.STAMP,
                diameterMM = null,
                internalDiameterMM = null,
                heightMM = "0",
                widthMM = "10"
            )
        ),
        currentPage = 0,
        totalPages = 0,
        totalRecords = 0,
    )

    @Autowired
    lateinit var context: WebApplicationContext

    @MockBean
    lateinit var staCoService: StaCoService

    @BeforeEach
    fun setup() {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build()
        `when`(
            staCoService.getAllInAllBySearchItem(
                searchItemValue = "%searchItem%",
                pageEntities = 0,
                pageSizeEntities = 10,
                sortColumn = "description",
                order = "asc"
            )
        )
            .thenReturn(testdata)
    }

    @Test
    fun testGetAllBySearchItem_whenCalling_thenGet1Person1Company() {
        val objectMapper = ObjectMapper()
        val expectedResponse = objectMapper.writeValueAsString(testdata)
        val result = this.mockMvc
            .perform(get("/api/staco/all/searchItem/0/10/description/asc"))
            .andExpect(status().isOk)
            .andExpect(
                content().json(expectedResponse)
            )
            .andReturn()

        val contentAsString = result.response.contentAsString
        assertThat(contentAsString).isNotNull

    }

    @Test
    fun testGetAllBySearchItem_whenCallingWithTooLongSearchItem_thenFail() {
        val exception = assertThrows<ServletException> {
            this.mockMvc
                .perform(get("/api/staco/all/aaaaaaaaaaaaaaaaaaa/0/10/description/asc"))

        }

        assertThat(exception).hasCauseExactlyInstanceOf(ConstraintViolationException::class.java)
        val constraintViolations = (exception.cause as ConstraintViolationException).constraintViolations
        assertThat(constraintViolations).hasSize(1)
        assertThat(constraintViolations.elementAt(0).message).isEqualTo("size must be between 1 and 10")
    }

    @Test
    fun testGetAllBySearchItem_whenCallingWithInvalidSearchItem_thenFail() {
        val exception = assertThrows<ServletException> {
            this.mockMvc
                .perform(get("/api/staco/all/%23%24%40/0/10/description/asc"))

        }

        assertThat(exception).hasCauseExactlyInstanceOf(ConstraintViolationException::class.java)
        val constraintViolations = (exception.cause as ConstraintViolationException).constraintViolations
        assertThat(constraintViolations).hasSize(1)
        assertThat(constraintViolations.elementAt(0).message).isEqualTo("must match \"[a-zA-Z0-9 ]*\"")
    }
}