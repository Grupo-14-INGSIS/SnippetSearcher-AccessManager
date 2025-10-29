package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.SharedSnippet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SharedSnippetRepository : JpaRepository<SharedSnippet, Long> {
    fun findBySnippetIdAndOwnerId(snippetId: Long, ownerId: String): List<SharedSnippet>
    fun findBySharedWithUserId(userId: String): List<SharedSnippet>
    fun findBySnippetId(snippetId: Long): List<SharedSnippet>
    fun existsBySnippetIdAndSharedWithUserId(snippetId: Long, userId: String): Boolean
    fun deleteBySnippetIdAndSharedWithUserId(snippetId: Long, userId: String)
}