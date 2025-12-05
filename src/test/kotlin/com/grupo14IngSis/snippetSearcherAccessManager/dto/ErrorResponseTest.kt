package com.grupo14IngSis.snippetSearcherAccessManager.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ErrorResponseTest {
    @Test
    fun `test ErrorResponse`() {
        val message = "Error message"
        val response = ErrorResponse(400, message, "details")

        assertEquals(400, response.status)
        assertEquals(message, response.message)
        assertEquals("details", response.details)
    }
}
