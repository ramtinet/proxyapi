package com.example.demo.dto

import com.example.demo.model.JobStatus
import java.util.UUID

data class JobDto(
    val id: UUID = UUID.randomUUID(),
    val programName: String,
    val size: Int,
    val webhookUrl: String? = null,
    var response: List<EpisodeDto>? = null,
    var status: JobStatus = JobStatus.PENDING
)