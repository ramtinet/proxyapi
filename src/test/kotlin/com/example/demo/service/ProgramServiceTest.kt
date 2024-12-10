package com.example.demo.service

import com.example.demo.dto.sr.episode.SrEpisodeResponseDto
import com.example.demo.dto.sr.program.SrProgramResponseDto
import com.example.demo.exeption.NotFoundException
import com.example.demo.service.sr.SrEpisodeService
import com.example.demo.service.sr.SrProgramService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import reactor.core.publisher.Mono
import reactor.test.StepVerifier


class ProgramServiceTest {

    private lateinit var srProgramService: SrProgramService
    private lateinit var srEpisodeService: SrEpisodeService
    private lateinit var programService: ProgramService

    private lateinit var mockSrEpisodeResponseDto: SrEpisodeResponseDto
    private lateinit var mockSrProgramResponseDto: SrProgramResponseDto

    private val programName = "Nyheter P4 Jämtland"
    private val programId = 78
    private val invalidProgramName = "Invalid Program"
    private val totalNrOfPrograms = 100
    private val totalNrOfEpisodes = 10

    @BeforeEach
    fun setup() {
        val objectMapper = ObjectMapper()
        srProgramService = mock()
        srEpisodeService = mock()
        val x200ProgramsResponseResource: Resource = ClassPathResource("test/200ProgramsResponse.json")
        val x200EpisodesResponseResource: Resource = ClassPathResource("test/200EpisodesResponse.json")
        mockSrEpisodeResponseDto = objectMapper.readValue(x200EpisodesResponseResource.file.readText(), SrEpisodeResponseDto::class.java)
        mockSrProgramResponseDto = objectMapper.readValue(x200ProgramsResponseResource.file.readText(), SrProgramResponseDto::class.java)

        whenever(srProgramService.getNrOfPrograms()).thenReturn(Mono.just(totalNrOfPrograms))
        whenever(srProgramService.listProgramsBySize(totalNrOfPrograms)).thenReturn(Mono.just(mockSrProgramResponseDto))
        whenever(srEpisodeService.fetchLatestEpisodes(programId, totalNrOfEpisodes)).thenReturn(Mono.just(mockSrEpisodeResponseDto))

        programService = ProgramService(srProgramService, srEpisodeService)
    }

    @Test
    fun `getLatestEpisodes should return episodes for a valid program name`() {
        StepVerifier.create(programService.getLatestEpisodes(programName, totalNrOfEpisodes))
            .assertNext { response ->
                Assertions.assertNotNull(response)
                Assertions.assertEquals(response, mockSrEpisodeResponseDto)
            }
            .verifyComplete()
    }

    @Test
    fun `getLatestEpisodes should return NotFoundException for an invalid program name`() {
        StepVerifier.create(programService.getLatestEpisodes(invalidProgramName, totalNrOfEpisodes))
            .expectErrorMatches { throwable ->
                throwable is NotFoundException && throwable.message.contains("Program not found")
            }
            .verify()
    }

    @Test
    fun `getProgramIdByName should return id for a valid program name`() {
        StepVerifier.create(programService.getProgramIdByName(programName))
            .assertNext { response ->
                Assertions.assertNotNull(response)
                Assertions.assertEquals(response, programId)
            }
            .verifyComplete()
    }

    @Test
    fun `getProgramIdByName should return NotFoundException for an invalid program namee`() {

        StepVerifier.create(programService.getProgramIdByName(invalidProgramName))
            .expectErrorMatches { throwable ->
                throwable is NotFoundException && throwable.message.contains("Program not found")
            }
            .verify()
    }

    @Test
    fun `listAllPrograms should return all programs`() {
        StepVerifier.create(programService.listAllPrograms())
            .assertNext { response ->
                Assertions.assertNotNull(response)
                Assertions.assertEquals(response, mockSrProgramResponseDto)
            }
            .verifyComplete()
    }
}