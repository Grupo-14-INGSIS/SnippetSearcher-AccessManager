package com.grupo14IngSis.snippetSearcherAccessManager.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class ShareIdTest {
    @Test
    fun `test ShareId`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val shareId = ShareId(snippetId, userId)

        assertEquals(snippetId, shareId.snippetId)
        assertEquals(userId, shareId.userId)
    }
}
