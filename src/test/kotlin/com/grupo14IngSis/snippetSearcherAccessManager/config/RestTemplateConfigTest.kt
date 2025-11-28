package com.grupo14IngSis.snippetSearcherAccessManager.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

class RestTemplateConfigTest {
    @Test
    fun `test restTemplate bean creation`() {
        val config = RestTemplateConfig()
        val restTemplate: RestTemplate = config.restTemplate()
        assertNotNull(restTemplate)
    }
}
