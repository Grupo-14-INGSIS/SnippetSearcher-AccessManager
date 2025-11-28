package com.grupo14IngSis.snippetSearcherAccessManager.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class GetPermissionsForUserResponseTest {
    @Test
    fun `test GetPermissionsForUserResponse`() {
        val userId = "user1"
        val owned = listOf(UUID.randomUUID().toString())
        val shared = listOf(UUID.randomUUID().toString())
        val response = GetPermissionsForUserResponse(userId, owned, shared)

        assertEquals(userId, response.userId)
        assertEquals(owned, response.owned)
        assertEquals(shared, response.shared)
    }
}
