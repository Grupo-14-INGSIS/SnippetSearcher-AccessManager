package com.grupo14IngSis.snippetSearcherAccessManager.domain

enum class Role {
    OWNER,
    SHARED;

    override fun toString(): String {
        return this.name.lowercase()
    }

    companion object {
        fun fromString(roleString: String): Role? {
            return entries.find { it.name.equals(roleString, ignoreCase = true) }
        }
    }
}
