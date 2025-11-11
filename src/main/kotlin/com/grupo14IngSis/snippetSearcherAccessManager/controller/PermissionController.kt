package com.grupo14IngSis.snippetSearcherAccessManager.controller

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role
import com.grupo14IngSis.snippetSearcherAccessManager.dto.PostPermissionRequest
import com.grupo14IngSis.snippetSearcherAccessManager.exceptions.BadRequestException
import com.grupo14IngSis.snippetSearcherAccessManager.service.PermissionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/permissions")
class PermissionController(private val permissionService: PermissionService) {
    @GetMapping
    fun getPermission(
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) snippetId: String?,
    ): ResponseEntity<*> {
        // GET permission for one snippet and one user
        if (userId != null && snippetId != null) {
            val response = permissionService.getPermission(snippetId = snippetId, userId = userId)
            return ResponseEntity.ok(response)
            // GET all for user
        } else if (userId != null) {
            val response = permissionService.getPermissionForUser(userId)
            return ResponseEntity.ok(response)
            // GET all for snippet
        } else if (snippetId != null) {
            val response = permissionService.getPermissionForSnippet(snippetId)
            return ResponseEntity.ok(response)
            // BadRequest
        } else {
            return ResponseEntity.badRequest().body("Must provide at least userId or snippetId.")
        }
    }

    @PostMapping
    fun createPermission(
        @RequestBody request: PostPermissionRequest,
    ): ResponseEntity<*> {
        try {
            val role: Role? = Role.fromString(request.role)
            if (role == null) {
                throw BadRequestException("${request.role} is not a valid role.")
            }
            val response =
                permissionService.addPermission(
                    snippetId = request.snippetId,
                    userId = request.userId,
                    role = role,
                )
            return ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (error: BadRequestException) {
            return ResponseEntity.badRequest().body(error)
        }
    }

    @DeleteMapping
    fun deletePermission(
        @RequestParam(required = false) userId: String?,
        @RequestParam(required = false) snippetId: String?,
    ): ResponseEntity<*> {
        // BadRequest
        if (userId == null && snippetId == null) {
            return ResponseEntity.badRequest().body("Must provide at least userId or snippetId.")
        }

        if (userId != null && snippetId != null) {
            // DELETE permission for one snippet and one user
            permissionService.deletePermission(snippetId = snippetId, userId = userId)
        } else if (userId != null) {
            // DELETE all for user
            permissionService.deletePermissionForUser(userId)
        } else if (snippetId != null) {
            // DELETE all for snippet
            permissionService.deletePermissionForSnippet(snippetId)
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Any>()
    }
}
