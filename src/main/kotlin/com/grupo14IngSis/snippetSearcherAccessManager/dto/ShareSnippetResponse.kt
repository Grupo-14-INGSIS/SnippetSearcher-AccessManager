package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class ShareSnippetResponse(
    val id: Long,
    val snippetId: Long,
    val ownerId: String,
    val sharedWithUserId: String,
    val permission: String,
    val sharedAt: String
)