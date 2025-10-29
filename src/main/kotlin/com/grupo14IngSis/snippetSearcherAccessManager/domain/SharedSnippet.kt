package com.grupo14IngSis.snippetSearcherAccessManager.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "shared_snippets")
data class SharedSnippet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val snippetId: Long,

    @Column(nullable = false)
    val ownerId: String,

    @Column(nullable = false)
    val sharedWithUserId: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val permission: Permission = Permission.READ,

    @Column(nullable = false)
    val sharedAt: LocalDateTime = LocalDateTime.now()
)