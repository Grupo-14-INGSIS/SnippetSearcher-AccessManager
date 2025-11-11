package com.grupo14IngSis.snippetSearcherAccessManager.controller

class ShareSnippetController

/*
import com.grupo14IngSis.snippetSearcherAccessManager.dto.*
import com.grupo14IngSis.snippetSearcherAccessManager.service.PermissionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/snippets/share")
class ShareSnippetController(
    private val shareSnippetService: PermissionService,
) {
    @PostMapping
    fun shareSnippet(
        @RequestBody request: ShareSnippetRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<ShareSnippetResponse> {
        val ownerId = jwt.subject

        return try {
            val response =
                shareSnippetService.shareSnippet(
                    snippetId = request.snippetId,
                    ownerId = ownerId,
                    sharedWithUserId = request.sharedWithUserId,
                )
            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/{snippetId}")
    fun getSharedUsers(
        @PathVariable snippetId: Long,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<List<ShareSnippetResponse>> {
        val ownerId = jwt.subject
        val sharedUsers = shareSnippetService.getSharedUsers(snippetId, ownerId)
        return ResponseEntity.ok(sharedUsers)
    }

    @GetMapping("/search-users")
    fun searchUsers(
        @RequestParam query: String,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<List<UserSearchResponse>> {
        val currentUserId = jwt.subject
        val users = shareSnippetService.searchUsers(query, currentUserId)
        return ResponseEntity.ok(users)
    }

    @DeleteMapping
    fun removeShare(
        @RequestBody request: RemoveShareRequest,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<Void> {
        val ownerId = jwt.subject

        return try {
            shareSnippetService.removeShare(
                snippetId = request.snippetId,
                ownerId = ownerId,
                sharedWithUserId = request.sharedWithUserId,
            )
            ResponseEntity.noContent().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping("/shared-with-me")
    fun getSnippetsSharedWithMe(
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<List<Long>> {
        val userId = jwt.subject
        val snippetIds = shareSnippetService.getSnippetsSharedWithUser(userId)
        return ResponseEntity.ok(snippetIds)
    }

    @GetMapping("/{snippetId}/has-access")
    fun hasAccess(
        @PathVariable snippetId: Long,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<Boolean> {
        val userId = jwt.subject
        val hasAccess = shareSnippetService.hasAccessToSnippet(snippetId, userId)
        return ResponseEntity.ok(hasAccess)
    }
}

 */
