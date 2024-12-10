
package com.example.demo.dto.sr.episode

import com.example.demo.dto.sr.Pagination
import com.fasterxml.jackson.annotation.JsonProperty

data class SrEpisodeResponseDto(
    @JsonProperty("copyright")
    val copyright: String,
    @JsonProperty("episodes")
    val episodes: List<Episode>,
    @JsonProperty("pagination")
    val pagination: Pagination
)






