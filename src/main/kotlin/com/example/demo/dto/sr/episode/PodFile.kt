package com.example.demo.dto.sr.episode

import com.fasterxml.jackson.annotation.JsonProperty

data class PodFile(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("filesizeinbytes")
    val filesizeinbytes: Int,
    @JsonProperty("program")
    val program: Program,
    @JsonProperty("availablefromutc")
    val availablefromutc: String,
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