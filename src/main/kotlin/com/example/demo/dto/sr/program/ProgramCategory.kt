package com.example.demo.dto.sr.program

import com.fasterxml.jackson.annotation.JsonProperty

data class ProgramCategory(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String
)