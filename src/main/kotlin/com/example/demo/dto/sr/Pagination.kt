package com.example.demo.dto.sr

import com.fasterxml.jackson.annotation.JsonProperty

data class Pagination(
    @JsonProperty("page")
    val page: Int,
    @JsonProperty("size")
    val size: Int,
    @JsonProperty("totalhits")
    val totalHits: Int,
    @JsonProperty("totalpages")
    val totalPages: Int,
    @JsonProperty("nextpage")
    val nextPage: String?
)