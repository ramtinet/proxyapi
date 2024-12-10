package com.example.demo.dto
import org.springframework.http.HttpStatus

data class ApiErrorDto(val message: String, val code: Int, val status: String) {
    constructor(message: String, status: HttpStatus) : this(message, status.value(), status.name)
}