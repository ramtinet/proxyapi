package com.example.demo.exeption

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException

/**
 * Exception thrown when a bad request is made.
 *
 * @param reason the reason for the exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(reason: String) : ResponseStatusException(HttpStatus.BAD_REQUEST, reason)