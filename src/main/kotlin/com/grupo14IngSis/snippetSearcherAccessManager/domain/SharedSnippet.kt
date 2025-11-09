package com.grupo14IngSis.snippetSearcherAccessManager.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "permission")
data class SharedSnippet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val snippetId: Long,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    val can_execute: Boolean = false,
)