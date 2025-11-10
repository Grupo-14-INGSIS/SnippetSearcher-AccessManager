package com.grupo14IngSis.snippetSearcherAccessManager.service

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Ownerships
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role
import com.grupo14IngSis.snippetSearcherAccessManager.domain.ShareId
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Shares
import com.grupo14IngSis.snippetSearcherAccessManager.dto.GetPermissionResponse
import com.grupo14IngSis.snippetSearcherAccessManager.dto.GetPermissionsForSnippetResponse
import com.grupo14IngSis.snippetSearcherAccessManager.dto.GetPermissionsForUserResponse
import com.grupo14IngSis.snippetSearcherAccessManager.dto.Permission
import com.grupo14IngSis.snippetSearcherAccessManager.exceptions.BadRequestException
import com.grupo14IngSis.snippetSearcherAccessManager.repository.OwnershipsRepository
import com.grupo14IngSis.snippetSearcherAccessManager.repository.SharesRepository
import com.grupo14IngSis.snippetSearcherAccessManager.repository.PermissionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PermissionService(
    private val ownershipsRepository: OwnershipsRepository,
    private val sharesRepository: SharesRepository,
    private val permissionRepository: PermissionRepository
) {

    fun getPermission(snippetId: String, userId: String): GetPermissionResponse {
        val permission: Permission? = permissionRepository.findPermission(snippetId=snippetId, userId=userId)
        if (permission == null) {
            return GetPermissionResponse(
                snippetId = snippetId,
                userId = userId,
                role = "none"
            )
        }
        return GetPermissionResponse(
            snippetId = snippetId,
            userId = userId,
            role = permission.role
        )
    }

    fun getPermissionForUser(userId: String): GetPermissionsForUserResponse {
        val permissions: List<Permission> = permissionRepository.findPermissionsByUserId(userId=userId)
        val owned: List<String> = permissions.filter{ it.role == "owner" }.map{ it.snippetId }
        val shared: List<String> = permissions.filter{ it.role == "shared" }.map{ it.snippetId }
        return GetPermissionsForUserResponse(
            userId = userId,
            owned = owned,
            shared = shared
        )
    }

    fun getPermissionForSnippet(snippetId: String): GetPermissionsForSnippetResponse {
        val permissions: List<Permission> = permissionRepository.findPermissionsBySnippetId(snippetId=snippetId)
        val ownerList: List<String> = permissions.filter{ it.role == "owner" }.map{ it.userId }
        val owner: String
        if (ownerList.isEmpty()) {
            owner = ""
        } else {
            owner = ownerList[0]
        }
        val shared: List<String> = permissions.filter{ it.role == "shared" }.map{ it.userId }
        return GetPermissionsForSnippetResponse(
            snippetId = snippetId,
            ownerId = owner,
            shared = shared
        )
    }

    @Transactional
    fun addPermission(snippetId: String, userId: String, role: Role): Permission {
        if (role == Role.OWNER) {
            val ownership = Ownerships(snippetId = snippetId, ownerId = userId)
            val existingOwner = ownershipsRepository.findById(snippetId)
            if (existingOwner.isPresent) {
                throw BadRequestException("Snippet with ID $snippetId already has an owner.")
            }
            val result = ownershipsRepository.save(ownership)
            return Permission(
                snippetId = result.snippetId,
                userId = result.ownerId,
                role = role.toString()
            )
        } else {
            val permission = Shares(snippetId = snippetId, userId = userId)
            val existingEntry = sharesRepository.findById(ShareId(snippetId, userId = userId))
            //val existingOwner = ownershipsRepository.findById(snippetId)
            if (existingEntry.isPresent) {
                throw BadRequestException("User with ID $userId already has permission for snippet with ID $snippetId.")
            }
            val result = sharesRepository.save(permission)
            return Permission(
                snippetId = result.snippetId,
                userId = result.userId,
                role = role.toString()
            )
        }
    }

    @Transactional
    fun deletePermission(snippetId: String, userId: String): Long {
        val deletedOwnerships = ownershipsRepository.deleteByOwnerIdAndSnippetId(ownerId = userId, snippetId = snippetId)
        val deletedShares = sharesRepository.deleteByUserIdAndSnippetId(snippetId = snippetId, userId = userId)
        return deletedOwnerships + deletedShares
    }

    @Transactional
    fun deletePermissionForUser(userId: String): Long {
        val deletedOwnerships = ownershipsRepository.deleteByOwnerId(ownerId = userId)
        val deletedShares = sharesRepository.deleteByUserId(userId = userId)
        return deletedOwnerships + deletedShares
    }

    @Transactional
    fun deletePermissionForSnippet(snippetId: String): Long {
        val deletedOwnerships = ownershipsRepository.deleteBySnippetId(snippetId = snippetId)
        val deletedShares = sharesRepository.deleteBySnippetId(snippetId = snippetId)
        return deletedOwnerships + deletedShares
    }

}