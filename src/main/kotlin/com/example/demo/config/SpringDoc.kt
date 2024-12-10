package com.example.demo.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets
import java.util.Optional

@Configuration
class SpringDoc {

    @Value("classpath:swagger/description.md")
    private lateinit var descriptionResource: Resource

    @Value("\${app.version}")
    private lateinit var applicationVersion: String

    /**
     * Creates a custom OpenAPI bean with the specified server URL.
     *
     * @param swaggerServerUrl the base URL for the Swagger UI
     * @return the configured OpenAPI instance
     */
    @Bean
    fun customOpenAPI(@Value("\${app.swagger-ui-basepath:#{null}}") swaggerServerUrl: String?): OpenAPI {
        return OpenAPI().info(getInfo()).servers(listOf(Server().url(Optional.ofNullable(swaggerServerUrl).orElse("http://localhost:8080/"))))
    }

    /**
     * Retrieves the API information including title, version, and description.
     *
     * @return the Info object containing API details
     */
    private fun getInfo(): Info {
        val description: String
        descriptionResource.inputStream.use { ins ->
            description = StreamUtils.copyToString(ins, StandardCharsets.UTF_8)
        }
        return Info().title("SR API").version(applicationVersion).description(description)
    }
}