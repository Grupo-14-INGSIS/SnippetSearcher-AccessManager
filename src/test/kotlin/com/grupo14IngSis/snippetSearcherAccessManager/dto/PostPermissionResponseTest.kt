package com.grupo14IngSis.snippetSearcherAccessManager.dto

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class PostPermissionResponseTest {
    @Test
    fun `test PostPermissionResponse`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val role = Role.OWNER
        val response = PostPermissionResponse(snippetId, userId, role)

        assertEquals(snippetId, response.snippetId)
        assertEquals(userId, response.userId)
        assertEquals(role, response.role)
    }
}
