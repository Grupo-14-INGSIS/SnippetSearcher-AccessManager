package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.ShareId
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Shares
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SharesRepository : JpaRepository<Shares, ShareId> {
    // save(entry: Shares)

    // findById(id: ShareId)

    @Transactional
    @Modifying
    fun deleteByUserIdAndSnippetId(
        userId: String,
        snippetId: String,
    ): Long

    @Transactional
    @Modifying
    fun deleteByUserId(userId: String): Long

    @Transactional
    @Modifying
    fun deleteBySnippetId(snippetId: String): Long
}
