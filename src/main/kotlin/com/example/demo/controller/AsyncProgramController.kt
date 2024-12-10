package com.example.demo.controller

import com.example.demo.dto.ApiErrorDto
import com.example.demo.dto.JobDto
import com.example.demo.exeption.NotFoundException
import com.example.demo.service.JobService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/async/programs")
class AsyncProgramController(private val jobService: JobService) {

    /**
     * Requests the latest episodes for a program.
     *
     * @param programName the name of the program
     * @param size the number of episodes to request
     * @param webhookUrl the webhook URL for notifications
     * @return a ResponseEntity containing the job ID
     */
    @Operation(summary = "Request latest episodes", description = "Request latest episodes for a program")
    @ApiResponse(responseCode = "200", description = "request created")
    @ApiResponse(responseCode = "400", description = "Bad request", content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorDto::class))])    @GetMapping("/latest-episodes")
    fun requestLatestEpisodes(
        @RequestParam("name", required = true) programName: String,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("webhookUrl") webhookUrl: String?
    ): ResponseEntity<String> {
        val jobId = jobService.createJob(programName, size, webhookUrl)
        return ResponseEntity.ok(jobId)
    }

    /**
     * Gets the status of the latest episodes request.
     *
     * @param id the job ID
     * @return a ResponseEntity containing the job status
     */
    @Operation(summary = "Get latest episodes", description = "Get latest episodes for a program")
    @ApiResponse(responseCode = "200", description = "Request status")
    @ApiResponse(responseCode = "404", description = "Request not found", content = [Content(mediaType = "application/json", schema = Schema(implementation = ApiErrorDto::class))])
    @GetMapping("/latest-episodes/{id}",)
    fun getLatestEpisodes(@PathVariable id: String): ResponseEntity<JobDto> {
        val job = jobService.getJobStatus(id)
        if(job == null){
            throw NotFoundException("Request not found: jobId=$id")
        }
        return ResponseEntity.ok(job)
    }
}