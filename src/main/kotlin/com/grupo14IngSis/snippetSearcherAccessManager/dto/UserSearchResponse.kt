package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class UserSearchResponse(
    val userId: String,
    val username: String,
    val role: String,
    val email: String?
)