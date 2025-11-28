package com.grupo14IngSis.snippetSearcherAccessManager.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class GetPermissionResponseTest {
    @Test
    fun `test GetPermissionResponse`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val role = "owner"
        val response = GetPermissionResponse(userId, snippetId, role)

        assertEquals(snippetId, response.snippetId)
        assertEquals(userId, response.userId)
        assertEquals(role, response.role)
    }
}
