package com.example.demo.dto.sr.episode

import com.fasterxml.jackson.annotation.JsonProperty

data class Episode(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("url")
    val url: String,
    @JsonProperty("program")
    val program: Program,
    @JsonProperty("audiopreference")
    val audiopreference: String,
    @JsonProperty("audiopriority")
    val audiopriority: String,
    @JsonProperty("audiopresentation")
    val audiopresentation: String,
    @JsonProperty("publishdateutc")
    val publishdateutc: String,
    @JsonProperty("imageurl")
    val imageurl: String,
    @JsonProperty("imageurltemplate")
    val imageurltemplate: String,
    @JsonProperty("photographer")
    val photographer: String?,
    @JsonProperty("broadcast")
    val broadcast: Broadcast?,
    @JsonProperty("broadcasttime")
    val broadcasttime: BroadcastTime?,
    @JsonProperty("listenpodfile")
    val listenpodfile: PodFile?,
    @JsonProperty("downloadpodfile")
    val downloadpodfile: PodFile?,
    @JsonProperty("channelid")
    val channelid: Int
)