package com.example.demo.handler

import com.example.demo.dto.ApiErrorDto
import com.example.demo.exeption.BadRequestException
import com.example.demo.exeption.NotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    val LOGGER: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    /**
     * Handles NotFoundException and returns a ResponseEntity with ApiErrorDto.
     *
     * @param ex the NotFoundException thrown
     * @param request the current web request
     * @return a ResponseEntity containing ApiErrorDto with NOT_FOUND status
     */
    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ApiErrorDto> {
        val reason = ex.getReason() ?: "Not found"
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, reason, request)
    }

    /**
     * Handles BadRequestException and returns a ResponseEntity with ApiErrorDto.
     *
     * @param ex the BadRequestException thrown
     * @param request the current web request
     * @return a ResponseEntity containing ApiErrorDto with BAD_REQUEST status
     */
    @ExceptionHandler(value = [BadRequestException::class])
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ApiErrorDto> {
        val reason = ex.getReason() ?: "Bad request"
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, reason, request)
    }

    /**
     * Handles MissingServletRequestParameterException and returns a ResponseEntity with an error message.
     *
     * @param ex the MissingServletRequestParameterException thrown
     * @param headers the HTTP headers
     * @param status the HTTP status code
     * @param request the current web request
     * @return a ResponseEntity containing an error message with BAD_REQUEST status
     */
    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, ex.message ?: "Missing parameter", request) as ResponseEntity<Any>
    }

    /**
     * Handles generic exceptions and returns a ResponseEntity with ApiErrorDto.
     *
     * @param ex the Exception thrown
     * @param request the current web request
     * @return a ResponseEntity containing ApiErrorDto with INTERNAL_SERVER_ERROR status
     */
    @ExceptionHandler(value = [Exception::class])
    fun handleExceptions(ex: Exception, request: WebRequest): ResponseEntity<ApiErrorDto> {
        val errorId = UUID.randomUUID().toString()
        LOGGER.error("Unexpected exception occurred: $errorId", ex)
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request, errorId)
    }

    /**
     * Builds an error response with the given parameters.
     *
     * @param ex the Exception thrown
     * @param httpStatus the HTTP status code
     * @param message the error message
     * @param request the current web request
     * @return a ResponseEntity containing ApiErrorDto with the specified status
     */
    fun buildErrorResponse(ex: Exception, httpStatus: HttpStatus, message: String, request: WebRequest): ResponseEntity<ApiErrorDto> {
        return buildErrorResponse(ex, httpStatus, message, request, UUID.randomUUID().toString())
    }

    /**
     * Builds an error response with the provided parameters and logs the error.
     *
     * @param ex the Exception thrown
     * @param httpStatus the HTTP status code
     * @param message the error message
     * @param request the current web request
     * @param errorId the unique error ID
     * @return a ResponseEntity containing ApiErrorDto with the specified status
     */
    fun buildErrorResponse(ex: Exception, httpStatus: HttpStatus, message: String, request: WebRequest, errorId: String): ResponseEntity<ApiErrorDto> {
        LOGGER.info("$message, errorId: $errorId", ex)
        return ResponseEntity.status(httpStatus).body(ApiErrorDto(message, httpStatus))
    }
}