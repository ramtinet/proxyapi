package com.example.demo.dto.sr.episode;
import com.fasterxml.jackson.annotation.JsonProperty

data class Program(
        @JsonProperty("id")
        val id: Int,
        @JsonProperty("name")
        val name: String
)
