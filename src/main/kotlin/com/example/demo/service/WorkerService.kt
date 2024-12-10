package com.example.demo.service

import com.example.demo.dto.sr.episode.SrEpisodeResponseDto
import com.example.demo.dto.JobDto
import com.example.demo.mapper.EpisodeMapper
import com.example.demo.model.JobStatus
import com.example.demo.queue.JobQueue
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import kotlin.concurrent.thread

@Service
class WorkerService(
    private val programService: ProgramService,
    private val webClient: WebClient,
    @Value("\${worker-service.delay:0}") private val delayMs: Long,
    @Value("\${worker-service.poll-interval:100}") private val pollIntervalMs: Long,
    )  {

    /**
     * Initializes and starts the worker thread that processes jobs from the job queue.
     */
    @PostConstruct
    fun startWorker() {
        thread(start = true) {
            while (true) {
                val job = JobQueue.queue.poll()
                if (job != null) {
                    processJob(job)
                }
                Thread.sleep(pollIntervalMs) // Avoid tight looping
            }
        }
    }

    /**
     * Processes a job by fetching the latest episodes for the given program name and updating the job status.
     *
     * @param job the job to be processed
     */
    private fun processJob(job: JobDto) {
        job.status = JobStatus.PROCESSING
        try {
            val episodeData = programService.getLatestEpisodes(job.programName, job.size).block()
            // Simulate processing time
            Thread.sleep(delayMs)
            if(episodeData == null) {
                job.status = JobStatus.FAILED
                return
            }

            val episodeDtoList = episodeData.episodes.let { EpisodeMapper.INSTANCE.episodesToEpisodeDtos(it) } ?: emptyList()
            job.response = episodeDtoList
            job.status = JobStatus.COMPLETED

            if (job.webhookUrl != null) {
                sendWebhook(job.webhookUrl, episodeData)
            }
        } catch (e: Exception) {
            job.status = JobStatus.FAILED
        }
    }

    /**
     * Sends a webhook notification with the episode data to the specified URL.
     *
     * @param url the webhook URL to send the notification to
     * @param episodeData the episode data to be sent in the webhook
     */
    private fun sendWebhook(url: String, episodeData: SrEpisodeResponseDto) {
        webClient.post()
            .uri(url)
            .bodyValue(episodeData)
            .retrieve()
            .bodyToMono(Void::class.java)
            .block()
    }
}