package com.example.demo.exeption

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

/**
 * Exception thrown when a requested resource is not found.
 *
 * @param reason the reason for the exception
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(reason: String) : ResponseStatusException(HttpStatus.NOT_FOUND, reason)