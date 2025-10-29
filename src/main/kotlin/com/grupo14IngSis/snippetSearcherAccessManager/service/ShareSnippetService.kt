package com.grupo14IngSis.snippetSearcherAccessManager.service

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Permission
import com.grupo14IngSis.snippetSearcherAccessManager.domain.SharedSnippet
import com.grupo14IngSis.snippetSearcherAccessManager.dto.ShareSnippetResponse
import com.grupo14IngSis.snippetSearcherAccessManager.dto.UserSearchResponse
import com.grupo14IngSis.snippetSearcherAccessManager.repository.SharedSnippetRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.format.DateTimeFormatter

@Service
class ShareSnippetService(
    private val sharedSnippetRepository: SharedSnippetRepository,
    private val userService: UserService
) {

    @Transactional
    fun shareSnippet(snippetId: Long, ownerId: String, sharedWithUserId: String): ShareSnippetResponse {
        // Validar que el usuario con quien se comparte exista
        if (!userService.userExists(sharedWithUserId)) {
            throw IllegalArgumentException("El usuario con ID $sharedWithUserId no existe")
        }

        // Validar que no se esté compartiendo consigo mismo
        if (ownerId == sharedWithUserId) {
            throw IllegalArgumentException("No puedes compartir un snippet contigo mismo")
        }

        // Validar que no se haya compartido previamente
        if (sharedSnippetRepository.existsBySnippetIdAndSharedWithUserId(snippetId, sharedWithUserId)) {
            throw IllegalArgumentException("El snippet ya está compartido con este usuario")
        }

        val sharedSnippet = SharedSnippet(
            snippetId = snippetId,
            ownerId = ownerId,
            sharedWithUserId = sharedWithUserId,
            permission = Permission.READ
        )

        val saved = sharedSnippetRepository.save(sharedSnippet)
        return mapToResponse(saved)
    }

    fun getSharedUsers(snippetId: Long, ownerId: String): List<ShareSnippetResponse> {
        return sharedSnippetRepository.findBySnippetIdAndOwnerId(snippetId, ownerId)
            .map { mapToResponse(it) }
    }

    fun getSnippetsSharedWithUser(userId: String): List<Long> {
        return sharedSnippetRepository.findBySharedWithUserId(userId)
            .map { it.snippetId }
    }

    fun searchUsers(query: String, currentUserId: String): List<UserSearchResponse> {
        return userService.searchUsers(query)
            .filter { it.userId != currentUserId }
    }

    @Transactional
    fun removeShare(snippetId: Long, ownerId: String, sharedWithUserId: String) {
        if (!sharedSnippetRepository.existsBySnippetIdAndSharedWithUserId(snippetId, sharedWithUserId)) {
            throw IllegalArgumentException("El snippet no está compartido con este usuario")
        }

        sharedSnippetRepository.deleteBySnippetIdAndSharedWithUserId(snippetId, sharedWithUserId)
    }

    fun hasAccessToSnippet(snippetId: Long, userId: String): Boolean {
        return sharedSnippetRepository.existsBySnippetIdAndSharedWithUserId(snippetId, userId)
    }

    private fun mapToResponse(sharedSnippet: SharedSnippet): ShareSnippetResponse {
        val user = userService.getUserById(sharedSnippet.sharedWithUserId)

        return ShareSnippetResponse(
            id = sharedSnippet.id,
            snippetId = sharedSnippet.snippetId,
            ownerId = sharedSnippet.ownerId,
            sharedWithUserId = sharedSnippet.sharedWithUserId,
            permission = sharedSnippet.permission.name,
            sharedAt = sharedSnippet.sharedAt.format(DateTimeFormatter.ISO_DATE_TIME)
        )
    }
}