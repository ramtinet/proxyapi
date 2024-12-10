package com.example.demo.dto.sr.episode

import com.fasterxml.jackson.annotation.JsonProperty

data class BroadcastTime(
    @JsonProperty("starttimeutc")
    val starttimeutc: String,
    @JsonProperty("endtimeutc")
    val endtimeutc: String
)
