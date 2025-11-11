package com.grupo14IngSis.snippetSearcherAccessManager.domain

enum class Role {
    OWNER,
    SHARED,
    ;

    override fun toString(): String {
        return this.name.lowercase()
    }

    companion object {
        fun fromString(role: String): Role? {
            return entries.find { it.name.equals(role, ignoreCase = true) }
        }
    }
}
