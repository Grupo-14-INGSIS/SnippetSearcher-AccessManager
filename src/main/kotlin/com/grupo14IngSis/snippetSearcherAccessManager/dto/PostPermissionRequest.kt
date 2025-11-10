package com.grupo14IngSis.snippetSearcherAccessManager.dto

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role

data class PostPermissionRequest(
    val snippetId: String,
    val userId: String,
    val role: Role
)