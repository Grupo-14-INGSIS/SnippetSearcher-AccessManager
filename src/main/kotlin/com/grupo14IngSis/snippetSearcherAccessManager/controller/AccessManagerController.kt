package com.grupo14IngSis.snippetSearcherAccessManager.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/internal/auth")
class AccessManagerController {

    /**
     * Endpoint para validar el token de autorización y devolver el ID de usuario.
     * Es llamado internamente por SnippetSearcher-App.
     * * @param authHeader El encabezado "Authorization" que contiene el token (e.g., "Bearer token-value").
     * @return ResponseEntity<String> con el ID de usuario (200 OK) o un 401 Unauthorized.
     */
    @GetMapping("/user-id")
    fun authorize(@RequestHeader("Authorization") authHeader: String): ResponseEntity<String> {

        // 1. **Simulación de Validación de Token**
        // En una implementación real con Auth0, aquí se verificaría el JWT.

        if (authHeader.startsWith("Bearer ") && authHeader.length > 10) {
            // Suponemos que cualquier token no vacío es "válido" por ahora.
            // Extraer el token (aunque no se use) y simular la obtención del ID.
            val token = authHeader.substringAfter("Bearer ")

            // 2. **Devolver ID de Usuario**
            // En un entorno real, el ID se decodificaría del token.
            return ResponseEntity.ok("user-id-from-auth0-456")
        }
        // Si no hay token o es inválido --> 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }
}