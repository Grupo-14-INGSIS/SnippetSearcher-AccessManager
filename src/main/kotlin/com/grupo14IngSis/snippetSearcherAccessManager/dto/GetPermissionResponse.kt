package com.grupo14IngSis.snippetSearcherAccessManager.dto

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Role

data class GetPermissionResponse(
    val userId: String,
    val snippetId: String,
    val role: String
)
