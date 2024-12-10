package com.example.demo.service.sr

import com.example.demo.dto.sr.episode.SrEpisodeResponseDto
import com.example.demo.util.ErrorHandlingUtils.errorHandling
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class SrEpisodeService(
    private val webClient: WebClient,
    @Value("\${sr.api.base-url}") private val srApiBaseUrl: String
) {
    /**
     * Fetches the latest episodes for a given program ID.
     *
     * @param programId the ID of the program
     * @param size the number of episodes to fetch
     * @return a `Mono<SrEpisodeResponseDto>` containing the latest episodes
     */
    fun fetchLatestEpisodes(programId: Int, size: Int): Mono<SrEpisodeResponseDto> {
        val date = LocalDate.now().toString()
        val url = UriComponentsBuilder.fromUriString("$srApiBaseUrl/episodes/index")
            .queryParam("programid", programId)
            .queryParam("todate", date)
            .queryParam("size", size)
            .queryParam("format", "json")
            .toUriString()
        return webClient.get()
            .uri(url)
            .retrieve()
            .onStatus({ status -> status.isError }, { response -> errorHandling(response, "Error when calling sr episodes api") })
            .bodyToMono(SrEpisodeResponseDto::class.java)
    }
}