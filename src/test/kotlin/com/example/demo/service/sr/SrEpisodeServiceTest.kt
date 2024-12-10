package com.example.demo.service.sr

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier


@SpringBootTest
class SrEpisodeServiceTest {

    @Value("classpath:test/200EpisodesResponse.json")
    private lateinit var x200EpisodesResponseResource: Resource
    @Value("classpath:test/400EpisodesResponse.json")
    private lateinit var x400EpisodesResponseResource: Resource

    private val programId = 78
    private val totalNrOfEpisodes = 10

    companion object {
        lateinit var mockWebServer: MockWebServer
        private const val baseUrl = "http://localhost:8083"

        @JvmStatic
        @BeforeAll
        fun setupMockWebServer() {
            mockWebServer = MockWebServer()
            mockWebServer.start(8083)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            mockWebServer.shutdown()
        }
    }

    private lateinit var subject: SrEpisodeService

    @BeforeEach
    fun setup() {
        subject = SrEpisodeService(WebClient.builder().build(), baseUrl)
    }

    @Test
    fun `fetchLatestEpisodes should return episode data`() {
        val response = x200EpisodesResponseResource.file.readText()
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).setBody(response))
        var responseMono = subject.fetchLatestEpisodes(programId, totalNrOfEpisodes)
        StepVerifier.create(responseMono)
            .assertNext({ episodeData ->
                Assertions.assertNotNull(episodeData)
                Assertions.assertEquals(episodeData.episodes.size, totalNrOfEpisodes)
            }).verifyComplete()
    }

    @Test
    fun `fetchLatestEpisodes should handle errors`() {
        val response = x400EpisodesResponseResource.file.readText()
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value()).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).setBody(response))
        val responseMono = subject.fetchLatestEpisodes(programId, totalNrOfEpisodes)
        StepVerifier.create(responseMono)
            .expectErrorMatches { throwable ->
                throwable is RuntimeException && throwable.message!!.contains("Error when calling sr episodes api")
            }
            .verify()
    }


}