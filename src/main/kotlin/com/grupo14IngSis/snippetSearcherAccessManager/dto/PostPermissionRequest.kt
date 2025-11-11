package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class PostPermissionRequest(
    val snippetId: String,
    val userId: String,
    val role: String,
)
