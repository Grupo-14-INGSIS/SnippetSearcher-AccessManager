package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Ownerships
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface OwnershipsRepository : JpaRepository<Ownerships, String> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Ownerships o WHERE o.ownerId = :ownerId AND o.snippetId = :snippetId")
    fun deleteByOwnerIdAndSnippetId(
        ownerId: String,
        snippetId: String,
    ): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM Ownerships o WHERE o.ownerId = :ownerId")
    fun deleteByOwnerId(ownerId: String): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM Ownerships o WHERE o.snippetId = :snippetId")
    fun deleteBySnippetId(snippetId: String): Int
}
