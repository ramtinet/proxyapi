package com.example.demo.dto.sr.program

import com.example.demo.dto.sr.Pagination
import com.fasterxml.jackson.annotation.JsonProperty

data class SrProgramResponseDto(
    @JsonProperty("copyright")
    val copyright: String,
    @JsonProperty("pagination")
    val pagination: Pagination,
    @JsonProperty("programs")
    val programs: List<Program>
)