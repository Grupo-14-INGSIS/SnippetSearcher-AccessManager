package com.grupo14IngSis.snippetSearcherAccessManager.service

import com.grupo14IngSis.snippetSearcherAccessManager.dto.UserSearchResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserService(
    private val restTemplate: RestTemplate
) {
    // URL del servicio de usuarios (ajustar según tu arquitectura)
    private val userServiceUrl = "http://user-service/api/users"

    fun searchUsers(query: String): List<UserSearchResponse> {
        // Implementación real (cuando tengas el servicio de usuarios):
        // return restTemplate.getForObject(
        //     "$userServiceUrl/search?q=$query",
        //     Array<UserSearchResponse>::class.java
        // )?.toList() ?: emptyList()

        // Mock temporal para desarrollo
        return listOf(
            UserSearchResponse("user1", "developer1", "DEVELOPER", "dev1@example.com"),
            UserSearchResponse("user2", "researcher1", "RESEARCHER", "res1@example.com"),
            UserSearchResponse("user3", "admin1", "ADMIN", "admin1@example.com"),
            UserSearchResponse("user4", "developer2", "DEVELOPER", "dev2@example.com"),
            UserSearchResponse("user5", "researcher2", "RESEARCHER", "res2@example.com")
        ).filter {
            it.username.contains(query, ignoreCase = true) ||
                    it.userId.contains(query, ignoreCase = true) ||
                    it.email?.contains(query, ignoreCase = true) == true
        }
    }

    fun userExists(userId: String): Boolean {
        // Implementación real:
        // return restTemplate.getForObject(
        //     "$userServiceUrl/$userId/exists",
        //     Boolean::class.java
        // ) ?: false

        // Mock temporal
        return userId.startsWith("user") ||
                userId in listOf("admin1", "developer1", "researcher1", "developer2", "researcher2")
    }

    fun getUserById(userId: String): UserSearchResponse {
        // Implementación real:
        // return restTemplate.getForObject(
        //     "$userServiceUrl/$userId",
        //     UserSearchResponse::class.java
        // )!!

        // Mock temporal
        return UserSearchResponse(
            userId = userId,
            username = "User $userId",
            role = "DEVELOPER",
            email = "$userId@example.com"
        )
    }
}