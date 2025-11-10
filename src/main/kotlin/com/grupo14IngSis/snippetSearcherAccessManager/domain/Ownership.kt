package com.grupo14IngSis.snippetSearcherAccessManager.domain

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.Column

@Entity
@Table(name = "ownerships")
data class Ownership(
    @Id
    val snippetId: String,

    @Column(nullable = false)
    val ownerId: String,
)