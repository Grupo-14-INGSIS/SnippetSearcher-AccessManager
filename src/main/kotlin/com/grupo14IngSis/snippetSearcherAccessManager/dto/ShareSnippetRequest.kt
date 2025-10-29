package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class ShareSnippetRequest(
    val snippetId: Long,
    val sharedWithUserId: String
)