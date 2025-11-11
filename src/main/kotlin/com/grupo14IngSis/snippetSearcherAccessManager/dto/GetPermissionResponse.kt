package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class GetPermissionResponse(
    val userId: String,
    val snippetId: String,
    val role: String,
)
