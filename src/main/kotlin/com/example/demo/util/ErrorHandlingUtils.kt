package com.example.demo.util

import org.springframework.web.reactive.function.client.ClientResponse
import reactor.core.publisher.Mono
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Handles error responses from external API requests.
 *
 * This method is used by `onStatus` in the services that are making external API requests
 * to handle error responses and log the error details.
 *
 * @param response the client response from the external API
 * @param message a custom error message to be included in the log
 * @return a `Mono<Throwable>` containing the error details
 */
object ErrorHandlingUtils {
    private val LOGGER: Logger = LoggerFactory.getLogger(ErrorHandlingUtils::class.java)

    fun errorHandling(response: ClientResponse, message: String): Mono<Throwable> {
        val status = response.statusCode().value()
        return response.bodyToMono(String::class.java).flatMap { body ->
            val errorMessage = "$message, status: $status, body: $body"
            LOGGER.error(errorMessage)
            Mono.error<Throwable>(RuntimeException(errorMessage))
        }
    }
}