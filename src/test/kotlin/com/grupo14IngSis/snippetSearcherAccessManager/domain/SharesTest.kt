package com.grupo14IngSis.snippetSearcherAccessManager.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class SharesTest {
    @Test
    fun `test Shares`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val shares = Shares(snippetId, userId)

        assertEquals(snippetId, shares.snippetId)
        assertEquals(userId, shares.userId)
    }
}
