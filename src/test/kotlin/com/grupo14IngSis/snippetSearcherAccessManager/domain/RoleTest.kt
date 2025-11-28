package com.grupo14IngSis.snippetSearcherAccessManager.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class RoleTest {
    @Test
    fun `test fromString with valid roles`() {
        assertEquals(Role.OWNER, Role.fromString("owner"))
        assertEquals(Role.SHARED, Role.fromString("shared"))
        assertEquals(Role.OWNER, Role.fromString("OWNER"))
        assertEquals(Role.SHARED, Role.fromString("SHARED"))
    }

    @Test
    fun `test fromString with invalid role`() {
        assertNull(Role.fromString("invalid"))
        assertNull(Role.fromString(""))
    }
}
