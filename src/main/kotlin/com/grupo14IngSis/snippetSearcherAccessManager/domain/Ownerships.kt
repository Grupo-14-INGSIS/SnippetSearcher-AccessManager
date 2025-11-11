package com.grupo14IngSis.snippetSearcherAccessManager.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "ownerships")
data class Ownerships(
    @Id
    val snippetId: String,
    @Column(nullable = false)
    val ownerId: String,
)
