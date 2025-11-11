package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.ShareId
import com.grupo14IngSis.snippetSearcherAccessManager.domain.Shares
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface SharesRepository : JpaRepository<Shares, ShareId> {

    // save(entry: Shares)

    // findById(id: ShareId)

    @Transactional
    fun deleteByUserIdAndSnippetId(userId: String, snippetId: String): Long

    @Transactional
    fun deleteByUserId(userId: String): Long

    @Transactional
    fun deleteBySnippetId(snippetId: String): Long
}