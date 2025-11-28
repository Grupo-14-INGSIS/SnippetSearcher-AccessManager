package com.grupo14IngSis.snippetSearcherAccessManager.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class PostPermissionRequestTest {
    @Test
    fun `test PostPermissionRequest`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val role = "owner"
        val request = PostPermissionRequest(snippetId, userId, role)

        assertEquals(snippetId, request.snippetId)
        assertEquals(userId, request.userId)
        assertEquals(role, request.role)
    }
}
