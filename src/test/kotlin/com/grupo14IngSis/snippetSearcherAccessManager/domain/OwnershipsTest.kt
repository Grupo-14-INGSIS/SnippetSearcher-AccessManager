package com.grupo14IngSis.snippetSearcherAccessManager.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID

class OwnershipsTest {
    @Test
    fun `test Ownerships`() {
        val snippetId = UUID.randomUUID().toString()
        val ownerId = "owner"
        val ownerships = Ownerships(snippetId, ownerId)

        assertEquals(snippetId, ownerships.snippetId)
        assertEquals(ownerId, ownerships.ownerId)
    }
}
