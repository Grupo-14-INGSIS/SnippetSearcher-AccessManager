package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.ShareId
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Shares
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SharesRepository : JpaRepository<Shares, ShareId> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Shares s WHERE s.userId = :userId AND s.snippetId = :snippetId")
    fun deleteByUserIdAndSnippetId(
        userId: String,
        snippetId: String,
    ): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM Shares s WHERE s.userId = :userId")
    fun deleteByUserId(userId: String): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM Shares s WHERE s.snippetId = :snippetId")
    fun deleteBySnippetId(snippetId: String): Int
}
