package com.grupo14IngSis.snippetSearcherAccessManager.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class GetPermissionsForSnippetResponseTest {
    @Test
    fun `test GetPermissionsForSnippetResponse`() {
        val snippetId = UUID.randomUUID().toString()
        val ownerId = "owner"
        val shared = listOf("user1", "user2")
        val response = GetPermissionsForSnippetResponse(snippetId, ownerId, shared)

        assertEquals(snippetId, response.snippetId)
        assertEquals(ownerId, response.ownerId)
        assertEquals(shared, response.shared)
    }
}
