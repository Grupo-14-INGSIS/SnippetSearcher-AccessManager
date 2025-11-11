package com.grupo14IngSis.snippetSearcherAccessManager.repository

import com.grupo14IngSis.snippetSearcherAccessManager.domain.Ownerships
import com.grupo14IngSis.snippetSearcherAccessManager.dto.Permission
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import org.springframework.data.repository.query.Param

interface PermissionRepository : Repository<Ownerships, String> {
    @Query(
        value = """
            SELECT o.snippet_id AS snippetId, o.owner_id AS userId, 'owner' as role
            FROM ownerships AS o
            WHERE o.snippet_id = :snippetId AND o.owner_id = :userId
            UNION ALL
            SELECT s.snippet_id AS snippetId, s.user_id AS userId, 'shared' as role
            FROM shares AS s
            WHERE s.snippet_id = :snippetId AND s.user_id = :userId
        """,
        nativeQuery = true,
    )
    fun findPermission(
        @Param("userId") userId: String,
        @Param("snippetId") snippetId: String,
    ): Permission?

    @Query(
        value = """
            SELECT o.snippet_id AS snippetId, o.owner_id AS userId, 'owner' as role FROM ownerships AS o WHERE o.owner_id = :userId
            UNION ALL
            SELECT s.snippet_id AS snippetId, s.user_id AS userId, 'shared' as role FROM shares AS s WHERE s.user_id = :userId
        """,
        nativeQuery = true,
    )
    fun findPermissionsByUserId(
        @Param("userId") userId: String,
    ): List<Permission>

    @Query(
        value = """
            SELECT o.snippet_id AS snippetId, o.owner_id AS userId, 'owner' as role FROM ownerships AS o WHERE o.snippet_id = :snippetId
            UNION ALL
            SELECT s.snippet_id AS snippetId, s.user_id AS userId, 'shared' as role FROM shares AS s WHERE s.snippet_id = :snippetId
        """,
        nativeQuery = true,
    )
    fun findPermissionsBySnippetId(
        @Param("snippetId") snippetId: String,
    ): List<Permission>
}
