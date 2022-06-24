package com.example.userservice

import com.example.userservice.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class SimpleJob(private val userRepository: UserRepository) {
    private val logger = LoggerFactory.getLogger(SimpleJob::class.java)

    @Scheduled(fixedDelay = 10_000L, initialDelay = 2_000)
    fun doWork() {
        logger.info("total users: ${userRepository.findAll().count()}")
    }
}