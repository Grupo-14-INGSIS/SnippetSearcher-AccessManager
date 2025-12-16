package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Ownerships
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface OwnershipsRepository : JpaRepository<Ownerships, String> {
    // save(entry: Ownerships)

    // findById(snippetId: String)

    @Transactional
    fun deleteByOwnerIdAndSnippetId(
        ownerId: String,
        snippetId: String,
    ): Long

    @Transactional
    fun deleteByOwnerId(ownerId: String): Long

    @Transactional
    @Modifying
    fun deleteBySnippetId(snippetId: String): Long
}
