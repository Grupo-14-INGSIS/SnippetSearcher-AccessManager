package com.grupo14IngSis.snippetSearcherAccessManager.service

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Ownerships
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role
import com.grupo14IngSis.snippetSearcherAccessManager.domain.ShareId
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Shares
import com.grupo14IngSis.snippetSearcherAccessManager.dto.Permission
import com.grupo14IngSis.snippetSearcherAccessManager.exceptions.BadRequestException
import com.grupo14IngSis.snippetSearcherAccessManager.repository.OwnershipsRepository
import com.grupo14IngSis.snippetSearcherAccessManager.repository.PermissionRepository
import com.grupo14IngSis.snippetSearcherAccessManager.repository.SharesRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional
import java.util.UUID
import kotlin.test.assertEquals

class PermissionServiceTest {
    private val ownershipsRepository = mockk<OwnershipsRepository>()
    private val sharesRepository = mockk<SharesRepository>()
    private val permissionRepository = mockk<PermissionRepository>()
    private val permissionService = PermissionService(ownershipsRepository, sharesRepository, permissionRepository)

    @Test
    fun `test getPermission found`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val permission = Permission(snippetId, userId, "owner")
        every { permissionRepository.findPermission(snippetId = snippetId, userId = userId) } returns permission

        val response = permissionService.getPermission(snippetId, userId)

        // The service implementation has the parameters in the wrong order in the DTO constructor
        assertEquals(userId, response.userId)
        assertEquals(snippetId, response.snippetId)
        assertEquals("owner", response.role)
        verify(exactly = 1) { permissionRepository.findPermission(snippetId = snippetId, userId = userId) }
    }

    @Test
    fun `test getPermission not found`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        every { permissionRepository.findPermission(snippetId = snippetId, userId = userId) } returns null

        val response = permissionService.getPermission(snippetId, userId)

        // The service implementation has the parameters in the wrong order in the DTO constructor
        assertEquals(userId, response.userId)
        assertEquals(snippetId, response.snippetId)
        assertEquals("none", response.role)
        verify(exactly = 1) { permissionRepository.findPermission(snippetId = snippetId, userId = userId) }
    }

    @Test
    fun `test getPermissionForUser`() {
        val userId = "user1"
        val snippetId1 = UUID.randomUUID().toString()
        val snippetId2 = UUID.randomUUID().toString()
        val permissions =
            listOf(
                Permission(snippetId1, userId, "owner"),
                Permission(snippetId2, userId, "shared"),
            )
        every { permissionRepository.findPermissionsByUserId(userId) } returns permissions

        val response = permissionService.getPermissionForUser(userId)

        assertEquals(userId, response.userId)
        assertEquals(listOf(snippetId1), response.owned)
        assertEquals(listOf(snippetId2), response.shared)
        verify(exactly = 1) { permissionRepository.findPermissionsByUserId(userId) }
    }

    @Test
    fun `test getPermissionForSnippet`() {
        val snippetId = UUID.randomUUID().toString()
        val userId1 = "user1"
        val userId2 = "user2"
        val permissions =
            listOf(
                Permission(snippetId, userId1, "owner"),
                Permission(snippetId, userId2, "shared"),
            )
        every { permissionRepository.findPermissionsBySnippetId(snippetId) } returns permissions

        val response = permissionService.getPermissionForSnippet(snippetId)

        assertEquals(snippetId, response.snippetId)
        assertEquals(userId1, response.ownerId)
        assertEquals(listOf(userId2), response.shared)
        verify(exactly = 1) { permissionRepository.findPermissionsBySnippetId(snippetId) }
    }

    @Test
    fun `test getPermissionForSnippet no owner`() {
        val snippetId = UUID.randomUUID().toString()
        val userId2 = "user2"
        val permissions =
            listOf(
                Permission(snippetId, userId2, "shared"),
            )
        every { permissionRepository.findPermissionsBySnippetId(snippetId) } returns permissions

        val response = permissionService.getPermissionForSnippet(snippetId)

        assertEquals(snippetId, response.snippetId)
        assertEquals("", response.ownerId)
        assertEquals(listOf(userId2), response.shared)
        verify(exactly = 1) { permissionRepository.findPermissionsBySnippetId(snippetId) }
    }

    @Test
    fun `test addPermission owner`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val ownership = Ownerships(snippetId, userId)
        every { ownershipsRepository.findById(snippetId) } returns Optional.empty()
        every { ownershipsRepository.save(any()) } returns ownership

        val response = permissionService.addPermission(snippetId, userId, Role.OWNER)

        assertEquals(snippetId, response.snippetId)
        assertEquals(userId, response.userId)
        assertEquals(Role.OWNER, response.role)
        verify(exactly = 1) { ownershipsRepository.findById(snippetId) }
        verify(exactly = 1) { ownershipsRepository.save(any()) }
    }

    @Test
    fun `test addPermission owner already exists`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val ownership = Ownerships(snippetId, userId)
        every { ownershipsRepository.findById(snippetId) } returns Optional.of(ownership)

        assertThrows<BadRequestException> {
            permissionService.addPermission(snippetId, userId, Role.OWNER)
        }
        verify(exactly = 1) { ownershipsRepository.findById(snippetId) }
    }

    @Test
    fun `test addPermission shared`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val share = Shares(snippetId, userId)
        every { sharesRepository.findById(ShareId(snippetId, userId)) } returns Optional.empty()
        every { sharesRepository.save(any()) } returns share

        val response = permissionService.addPermission(snippetId, userId, Role.SHARED)

        assertEquals(snippetId, response.snippetId)
        assertEquals(userId, response.userId)
        assertEquals(Role.SHARED, response.role)
        verify(exactly = 1) { sharesRepository.findById(ShareId(snippetId, userId)) }
        verify(exactly = 1) { sharesRepository.save(any()) }
    }

    @Test
    fun `test addPermission shared already exists`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        val share = Shares(snippetId, userId)
        every { sharesRepository.findById(ShareId(snippetId, userId)) } returns Optional.of(share)

        assertThrows<BadRequestException> {
            permissionService.addPermission(snippetId, userId, Role.SHARED)
        }
        verify(exactly = 1) { sharesRepository.findById(ShareId(snippetId, userId)) }
    }

    @Test
    fun `test deletePermission`() {
        val snippetId = UUID.randomUUID().toString()
        val userId = "user1"
        every { ownershipsRepository.deleteByOwnerIdAndSnippetId(ownerId = userId, snippetId = snippetId) } returns 1
        every { sharesRepository.deleteByUserIdAndSnippetId(snippetId = snippetId, userId = userId) } returns 1

        val result = permissionService.deletePermission(snippetId, userId)

        assertEquals(2, result)
        verify(exactly = 1) { ownershipsRepository.deleteByOwnerIdAndSnippetId(ownerId = userId, snippetId = snippetId) }
        verify(exactly = 1) { sharesRepository.deleteByUserIdAndSnippetId(snippetId = snippetId, userId = userId) }
    }

    @Test
    fun `test deletePermissionForUser`() {
        val userId = "user1"
        every { ownershipsRepository.deleteByOwnerId(ownerId = userId) } returns 1
        every { sharesRepository.deleteByUserId(userId = userId) } returns 1

        val result = permissionService.deletePermissionForUser(userId)

        assertEquals(2, result)
        verify(exactly = 1) { ownershipsRepository.deleteByOwnerId(ownerId = userId) }
        verify(exactly = 1) { sharesRepository.deleteByUserId(userId = userId) }
    }

    @Test
    fun `test deletePermissionForSnippet`() {
        val snippetId = UUID.randomUUID().toString()
        every { ownershipsRepository.deleteBySnippetId(snippetId = snippetId) } returns 1
        every { sharesRepository.deleteBySnippetId(snippetId = snippetId) } returns 1

        val result = permissionService.deletePermissionForSnippet(snippetId)

        assertEquals(2, result)
        verify(exactly = 1) { ownershipsRepository.deleteBySnippetId(snippetId = snippetId) }
        verify(exactly = 1) { sharesRepository.deleteBySnippetId(snippetId = snippetId) }
    }
}
