package com.grupo14IngSis.snippetSearcherAccessManager.exceptions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BadRequestExceptionTest {
    @Test
    fun `test BadRequestException`() {
        val message = "Bad request"
        val exception = BadRequestException(message)

        assertEquals(message, exception.message)
    }
}
