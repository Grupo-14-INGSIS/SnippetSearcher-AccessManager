package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class Permission(
    val snippetId: String,
    val userId: String,
    val role: String,
)
