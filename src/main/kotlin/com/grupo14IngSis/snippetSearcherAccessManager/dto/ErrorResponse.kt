package com.grupo14IngSis.snippetSearcherAccessManager.dto

data class ErrorResponse(
    val status: Int,
    val message: String,
    val details: String,
)
