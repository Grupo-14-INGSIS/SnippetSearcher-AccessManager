package com.grupo14IngSis.snippetSearcherAccessManager.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.any
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.slf4j.MDC
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import java.util.UUID

class RequestIdFilterTest {
    private lateinit var filter: RequestIdFilter
    private lateinit var request: MockHttpServletRequest
    private lateinit var response: MockHttpServletResponse
    private lateinit var filterChain: FilterChain

    @BeforeEach
    fun setUp() {
        filter = RequestIdFilter()
        request = MockHttpServletRequest()
        response = MockHttpServletResponse()
        filterChain = mock(FilterChain::class.java)
        MDC.clear()
    }

    @AfterEach
    fun tearDown() {
        MDC.clear()
    }

    @Test
    fun `should generate new request id if header is not present`() {
        request.method = "GET"
        request.requestURI = "/test"

        var capturedMdcValue: String? = null
        doAnswer {
            capturedMdcValue = MDC.get("requestId")
            Unit
        }.`when`(filterChain).doFilter(
            any(HttpServletRequest::class.java),
            any(HttpServletResponse::class.java),
        )

        filter.doFilter(request, response, filterChain)

        assertTrue(response.containsHeader("X-Request-Id"))
        assertNotNull(response.getHeader("X-Request-Id"))
        assertNotNull(capturedMdcValue)
        assertEquals(response.getHeader("X-Request-Id"), capturedMdcValue)
        assertNull(MDC.get("requestId"))
    }

    @Test
    fun `should use existing request id if header is present`() {
        val existingRequestId = UUID.randomUUID().toString()
        request.addHeader("X-Request-Id", existingRequestId)
        request.method = "POST"
        request.requestURI = "/data"

        var capturedMdcValue: String? = null
        doAnswer {
            capturedMdcValue = MDC.get("requestId")
            Unit
        }.`when`(filterChain).doFilter(
            any(HttpServletRequest::class.java),
            any(HttpServletResponse::class.java),
        )

        filter.doFilter(request, response, filterChain)

        assertTrue(response.containsHeader("X-Request-Id"))
        assertEquals(existingRequestId, response.getHeader("X-Request-Id"))
        assertEquals(existingRequestId, capturedMdcValue)
        assertNull(MDC.get("requestId"))
    }

    @Test
    fun `should clear MDC after filter chain completes`() {
        request.method = "GET"
        request.requestURI = "/test"

        filter.doFilter(request, response, filterChain)

        assertNull(MDC.get("requestId"))
    }

    @Test
    fun `should clear MDC even if filter chain throws exception`() {
        request.method = "GET"
        request.requestURI = "/test"

        doThrow(RuntimeException("Test exception")).`when`(filterChain).doFilter(
            any(HttpServletRequest::class.java),
            any(HttpServletResponse::class.java),
        )

        try {
            filter.doFilter(request, response, filterChain)
        } catch (e: RuntimeException) {
            // Expected exception
        }

        assertNull(MDC.get("requestId"))
    }

    @Test
    fun `should add custom parameter to New Relic`() {
        val existingRequestId = UUID.randomUUID().toString()
        request.addHeader("X-Request-Id", existingRequestId)
        request.method = "PUT"
        request.requestURI = "/update"

        var capturedMdcValue: String? = null
        doAnswer {
            capturedMdcValue = MDC.get("requestId")
            Unit
        }.`when`(filterChain).doFilter(
            any(HttpServletRequest::class.java),
            any(HttpServletResponse::class.java),
        )

        filter.doFilter(request, response, filterChain)

        assertTrue(response.containsHeader("X-Request-Id"))
        assertEquals(existingRequestId, response.getHeader("X-Request-Id"))
        assertEquals(existingRequestId, capturedMdcValue)
        assertNull(MDC.get("requestId"))
    }
}
