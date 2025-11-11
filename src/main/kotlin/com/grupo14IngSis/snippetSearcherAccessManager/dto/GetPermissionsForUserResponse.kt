package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class GetPermissionsForUserResponse(
    val userId: String,
    val owned: List<String>,
    val shared: List<String>
)
