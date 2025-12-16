package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.ShareId
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Shares
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SharesRepository : JpaRepository<Shares, ShareId> {
    // save(entry: Shares)

    // findById(id: ShareId)

    @Transactional
    @Modifying
    @Query("DELETE FROM Shares s WHERE s.userId = :userId AND s.snippetId = :snippetId")
    fun deleteByUserIdAndSnippetId(
        @Param("userId") userId: String,
        @Param("snippetId") snippetId: String,
    ): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM Shares s WHERE s.userId = :userId")
    fun deleteByUserId(
        @Param("userId") userId: String,
    ): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM Shares s WHERE s.snippetId = :snippetId")
    fun deleteBySnippetId(
        @Param("snippetId") snippetId: String,
    ): Int
}
