package com.example.demo.service

import com.example.demo.dto.sr.episode.SrEpisodeResponseDto
import com.example.demo.dto.sr.program.SrProgramResponseDto
import com.example.demo.exeption.NotFoundException
import com.example.demo.service.sr.SrEpisodeService
import com.example.demo.service.sr.SrProgramService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Service
class ProgramService(
    private val srProgramService: SrProgramService,
    private val srEpisodeService: SrEpisodeService
) {
    private val programIdCache = ConcurrentHashMap<String, Int>()

    /**
     * Fetches the latest episodes for a given program name.
     *
     * @param programName the name of the program
     * @param size the number of episodes to fetch
     * @return a `Mono<SrEpisodeResponseDto>` containing the latest episodes
     * @throws NotFoundException if the program is not found
     */
    fun getLatestEpisodes(programName: String, size: Int): Mono<SrEpisodeResponseDto> {

        return getProgramIdByName(programName)
            .switchIfEmpty(Mono.error(NotFoundException("Program not found, $programName")))
            .flatMap { programId ->
                srEpisodeService.fetchLatestEpisodes(programId, size)
            }
    }

    /**
     * Retrieves the program ID for a given program name.
     *
     * @param name the name of the program
     * @return a `Mono<Int>` containing the program ID
     * @throws NotFoundException if the program is not found
     */
    fun getProgramIdByName(name: String): Mono<Int> {
        return Mono.justOrEmpty(programIdCache[name])
            .switchIfEmpty(
                listAllPrograms()
                    .flatMap { programData ->
                        val program = programData.programs.find { it.name == name }
                        if (program != null) {
                            Mono.just(program.id.toInt())
                        } else {
                            Mono.error(NotFoundException("Program not found"))
                        }
                    }.doOnNext { id ->
                        programIdCache[name] = id
                    }
            )
    }

    /**
     * Lists all programs available.
     *
     * @return a `Mono<SrProgramResponseDto>` containing all programs
     */
    fun listAllPrograms(): Mono<SrProgramResponseDto> {
        return srProgramService.getNrOfPrograms()
            .flatMap { nrOfPrograms ->
                srProgramService.listProgramsBySize(nrOfPrograms)
            }
    }
}