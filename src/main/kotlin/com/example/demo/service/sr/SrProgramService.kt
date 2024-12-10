package com.example.demo.service.sr

import com.example.demo.dto.sr.program.SrProgramResponseDto
import com.example.demo.util.ErrorHandlingUtils.errorHandling
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Service
class SrProgramService(
    private val webClient: WebClient,
    @Value("\${sr.api.base-url}") private val srApiBaseUrl: String
) {
    /**
     * Lists programs by the specified size.
     *
     * @param size the number of programs to list
     * @return a `Mono<SrProgramResponseDto>` containing the list of programs
     */
    fun listProgramsBySize(size: Int): Mono<SrProgramResponseDto> {
        val url = UriComponentsBuilder.fromUriString("$srApiBaseUrl/programs")
            .queryParam("size", size)
            .queryParam("format", "json")
            .toUriString()
        return webClient.get()
            .uri(url)
            .retrieve()
            .onStatus({ status -> status.isError }, { response -> errorHandling(response, "Error when calling sr programs api") })
            .bodyToMono(SrProgramResponseDto::class.java)
    }

    /**
     * Retrieves the number of programs available.
     *
     * @return a `Mono<Int>` containing the number of programs
     */
    fun getNrOfPrograms(): Mono<Int> {
        val url = UriComponentsBuilder.fromUriString("$srApiBaseUrl/programs")
            .queryParam("size", 1)
            .queryParam("format", "json")
            .toUriString()
        return webClient.get()
            .uri(url)
            .retrieve()
            .onStatus({ status -> status.isError }, { response -> errorHandling(response, "Error when calling sr programs api") })
            .bodyToMono(SrProgramResponseDto::class.java)
            .map { response -> response.pagination.totalHits }
    }
}