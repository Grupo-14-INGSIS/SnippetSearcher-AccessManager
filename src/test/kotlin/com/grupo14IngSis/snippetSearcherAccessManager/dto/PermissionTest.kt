package com.grupo14IngSis.snippetSearcherAccessManager.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class PermissionTest {
    @Test
    fun `test Permission`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val role = "owner"
        val permission = Permission(snippetId, userId, role)

        assertEquals(snippetId, permission.snippetId)
        assertEquals(userId, permission.userId)
        assertEquals(role, permission.role)
    }
}
