package com.example.demo.dto.sr.episode

import com.fasterxml.jackson.annotation.JsonProperty

data class Playlist (
    @JsonProperty("duration")
    val duration: Int,
    @JsonProperty("publishdateutc")
    val publishdateutc: String,
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("statkey")
    val statkey: String
)