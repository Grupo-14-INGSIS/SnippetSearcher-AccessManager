package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class Permission(
    val userId: String,
    val snippetId: String,
    val role: String,
)
