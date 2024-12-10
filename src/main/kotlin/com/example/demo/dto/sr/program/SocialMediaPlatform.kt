package com.example.demo.dto.sr.program

import com.fasterxml.jackson.annotation.JsonProperty

data class SocialMediaPlatform(
    @JsonProperty("platform")
    val platform: String,
    @JsonProperty("platformurl")
    val platformUrl: String
)