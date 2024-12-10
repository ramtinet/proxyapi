package com.example.demo.controller

import com.example.demo.dto.EpisodeDto
import com.example.demo.mapper.EpisodeMapper
import com.example.demo.service.ProgramService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/sync/programs")
class ProgramController(
    private val programService: ProgramService,

) {
    /**
     * Gets the latest episodes for a program.
     *
     * @param programName the name of the program
     * @param size the number of episodes to retrieve
     * @return a ResponseEntity containing a list of EpisodeDto objects
     */
    @Operation(summary = "Get latest episodes", description = "Get latest episodes for a program")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @GetMapping("/latest-episodes", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getLatestEpisodes(@RequestParam("name", required = true) programName: String, @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<List<EpisodeDto>> {
        val episodeData = programService.getLatestEpisodes(programName, size).block()
        val episodeDtoList = episodeData?.episodes?.let { EpisodeMapper.INSTANCE.episodesToEpisodeDtos(it) } ?: emptyList()
        return ResponseEntity.ok(episodeDtoList)
    }
}