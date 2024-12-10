package com.example.demo.mapper

import com.example.demo.dto.EpisodeDto
import com.example.demo.dto.sr.episode.Episode
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers

@Mapper
interface EpisodeMapper {
    companion object {
        val INSTANCE: EpisodeMapper = Mappers.getMapper(EpisodeMapper::class.java)
    }

    /**
     * Maps a list of Episode objects to a list of EpisodeDto objects.
     *
     * @param episodes the list of Episode objects to map
     * @return a list of EpisodeDto objects
     */
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "publishdateutc", source = "publishdateutc")
    fun episodesToEpisodeDtos(episodes: List<Episode>): List<EpisodeDto>
}