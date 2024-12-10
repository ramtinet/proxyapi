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
class SrProgramServiceTest {

    @Value("classpath:test/200ProgramsResponse.json")
    private lateinit var x200ProgramsResponseResource: Resource
    @Value("classpath:test/404ProgramsResponse.json")
    private lateinit var x404ProgramsResponseResource: Resource

    private val totalNrOfPrograms = 100

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

    private lateinit var subject: SrProgramService

    @BeforeEach
    fun setup() {
        subject = SrProgramService(WebClient.builder().build(), baseUrl)
    }

    @Test
    fun `listProgramsBySize should return programs data`() {
        val response = x200ProgramsResponseResource.file.readText()
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).setBody(response))
        var responseMono = subject.listProgramsBySize(totalNrOfPrograms)
        StepVerifier.create(responseMono)
            .assertNext({ programData ->
                Assertions.assertNotNull(programData)
                Assertions.assertEquals(programData.programs.size, totalNrOfPrograms)
            }).verifyComplete()
    }

    @Test
    fun `listProgramsBySize should handle errors`() {
        val response = x404ProgramsResponseResource.file.readText()
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value()).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).setBody(response))
        val responseMono = subject.listProgramsBySize(totalNrOfPrograms)
        StepVerifier.create(responseMono)
            .expectErrorMatches { throwable ->
                throwable is RuntimeException && throwable.message!!.contains("Error when calling sr programs api")
            }
            .verify()
    }


    @Test
    fun `getNrOfPrograms should return the number of programs`() {
        val response = x200ProgramsResponseResource.file.readText()
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.OK.value()).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).setBody(response))
        val responseMono = subject.getNrOfPrograms()
        StepVerifier.create(responseMono)
            .assertNext { nrOfPrograms ->
                Assertions.assertNotNull(nrOfPrograms)
                Assertions.assertTrue(nrOfPrograms > 0)
            }
            .verifyComplete()
    }

    @Test
    fun `getNrOfPrograms should handle errors`() {
        val response = x404ProgramsResponseResource.file.readText()
        mockWebServer.enqueue(MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value()).setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).setBody(response))
        val responseMono =  subject.getNrOfPrograms()
        StepVerifier.create(responseMono)
            .expectErrorMatches { throwable ->
                throwable is RuntimeException && throwable.message!!.contains("Error when calling sr programs api")
            }
            .verify()
    }
}