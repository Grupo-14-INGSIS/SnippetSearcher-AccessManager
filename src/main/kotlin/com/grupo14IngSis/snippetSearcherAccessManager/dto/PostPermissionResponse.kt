package com.grupo14IngSis.snippetSearcherAccessManager.dto

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role

data class PostPermissionResponse(
    val snippetId: String,
    val userId: String,
    val role: Role
)
