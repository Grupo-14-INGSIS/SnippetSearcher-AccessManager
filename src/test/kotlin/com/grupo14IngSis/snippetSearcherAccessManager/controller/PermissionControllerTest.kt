package com.grupo14IngSis.snippetSearcherAccessManager.controller

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role
import com.grupo14IngSis.snippetSearcherAccessManager.dto.GetPermissionResponse
import com.grupo14IngSis.snippetSearcherAccessManager.dto.GetPermissionsForSnippetResponse
import com.grupo14IngSis.snippetSearcherAccessManager.dto.GetPermissionsForUserResponse
import com.grupo14IngSis.snippetSearcherAccessManager.dto.PostPermissionRequest
import com.grupo14IngSis.snippetSearcherAccessManager.dto.PostPermissionResponse
import com.grupo14IngSis.snippetSearcherAccessManager.service.PermissionService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.util.UUID

class PermissionControllerTest {
    private val permissionService = mockk<PermissionService>()
    private val permissionController = PermissionController(permissionService)

    @Test
    fun `test getPermission with userId and snippetId`() {
        val userId = "testUser"
        val snippetId = UUID.randomUUID().toString()
        val getPermissionResponse = GetPermissionResponse(snippetId, userId, "owner")

        every { permissionService.getPermission(snippetId, userId) } returns getPermissionResponse

        val response = permissionController.getPermission(userId, snippetId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(getPermissionResponse, response.body)
        verify(exactly = 1) { permissionService.getPermission(snippetId, userId) }
    }

    @Test
    fun `test getPermission with only userId`() {
        val userId = "testUser"
        val getPermissionsForUserResponse = GetPermissionsForUserResponse(userId, listOf(UUID.randomUUID().toString()), emptyList())

        every { permissionService.getPermissionForUser(userId) } returns getPermissionsForUserResponse

        val response = permissionController.getPermission(userId, null)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(getPermissionsForUserResponse, response.body)
        verify(exactly = 1) { permissionService.getPermissionForUser(userId) }
    }

    @Test
    fun `test getPermission with only snippetId`() {
        val snippetId = UUID.randomUUID().toString()
        val getPermissionsForSnippetResponse = GetPermissionsForSnippetResponse(snippetId, "testUser", emptyList())

        every { permissionService.getPermissionForSnippet(snippetId) } returns getPermissionsForSnippetResponse

        val response = permissionController.getPermission(null, snippetId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(getPermissionsForSnippetResponse, response.body)
        verify(exactly = 1) { permissionService.getPermissionForSnippet(snippetId) }
    }

    @Test
    fun `test getPermission with no params`() {
        val response = permissionController.getPermission(null, null)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Must provide at least userId or snippetId.", response.body)
    }

    @Test
    fun `test createPermission`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "testUser"
        val role = "owner"
        val postPermissionRequest = PostPermissionRequest(snippetId, userId, role)
        val postPermissionResponse = PostPermissionResponse(snippetId, userId, Role.OWNER)

        every { permissionService.addPermission(snippetId, userId, Role.OWNER) } returns postPermissionResponse

        val response = permissionController.createPermission(postPermissionRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(postPermissionResponse, response.body)
        verify(exactly = 1) { permissionService.addPermission(snippetId, userId, Role.OWNER) }
    }

    @Test
    fun `test createPermission with invalid role`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "testUser"
        val role = "invalidRole"
        val postPermissionRequest = PostPermissionRequest(snippetId, userId, role)

        val response = permissionController.createPermission(postPermissionRequest)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertNotNull(response.body)
    }

    @Test
    fun `test deletePermission with userId and snippetId`() {
        val userId = "testUser"
        val snippetId = UUID.randomUUID().toString()

        every { permissionService.deletePermission(snippetId, userId) } returns 1

        val response = permissionController.deletePermission(userId, snippetId)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(exactly = 1) { permissionService.deletePermission(snippetId, userId) }
    }

    @Test
    fun `test deletePermission with only userId`() {
        val userId = "testUser"

        every { permissionService.deletePermissionForUser(userId) } returns 1

        val response = permissionController.deletePermission(userId, null)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(exactly = 1) { permissionService.deletePermissionForUser(userId) }
    }

    @Test
    fun `test deletePermission with only snippetId`() {
        val snippetId = UUID.randomUUID().toString()

        every { permissionService.deletePermissionForSnippet(snippetId) } returns 1

        val response = permissionController.deletePermission(null, snippetId)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(exactly = 1) { permissionService.deletePermissionForSnippet(snippetId) }
    }

    @Test
    fun `test deletePermission with no params`() {
        val response = permissionController.deletePermission(null, null)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("Must provide at least userId or snippetId.", response.body)
    }
}
