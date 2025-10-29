package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class RemoveShareRequest(
    val snippetId: Long,
    val sharedWithUserId: String
)