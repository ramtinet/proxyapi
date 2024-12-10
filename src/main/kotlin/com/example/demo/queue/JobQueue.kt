package com.example.demo.queue

import com.example.demo.dto.JobDto
import java.util.concurrent.ConcurrentLinkedQueue

object JobQueue {
    /**
     * A thread-safe queue to hold job data transfer objects.
     */
    val queue: ConcurrentLinkedQueue<JobDto> = ConcurrentLinkedQueue()
}