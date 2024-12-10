package com.example.demo.dto.sr.episode

import com.fasterxml.jackson.annotation.JsonProperty

data class Broadcast (
    @JsonProperty("availablestoputc")
    val availablestoputc: String,
    @JsonProperty("playlist")
    val playlist: Playlist,
    @JsonProperty("broadcastfiles")
    val broadcastfiles: List<BroadcastFile>
)