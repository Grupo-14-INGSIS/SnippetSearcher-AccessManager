package com.grupo14IngSis.snippetSearcherAccessManager.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

@Entity
@Table(name = "shares")
@IdClass(ShareId::class)
data class Shares(
    @Id
    val snippetId: String,

    @Id
    val userId: String,
)