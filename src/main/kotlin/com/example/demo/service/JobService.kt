package com.example.demo.service

import com.example.demo.dto.JobDto
import com.example.demo.queue.JobQueue
import org.springframework.stereotype.Service

@Service
class JobService {

    private val jobStorage: MutableMap<String, JobDto> = mutableMapOf()

    /**
     * Creates a new job and adds it to the job queue.
     *
     * @param programName the name of the program for which the job is created
     * @param size the number of episodes to fetch
     * @param webhookUrl the URL to send the webhook notification to, if any
     * @return the ID of the created job
     */
    fun createJob(programName: String, size: Int, webhookUrl: String?): String {
        val job = JobDto(programName = programName, size = size, webhookUrl = webhookUrl)
        JobQueue.queue.add(job)
        jobStorage[job.id.toString()] = job
        return job.id.toString()
    }

    /**
     * Retrieves the status of a job by its ID.
     *
     * @param jobId the ID of the job
     * @return the job details, or null if the job is not found
     */
    fun getJobStatus(jobId: String): JobDto? {
        return jobStorage[jobId]
    }
}