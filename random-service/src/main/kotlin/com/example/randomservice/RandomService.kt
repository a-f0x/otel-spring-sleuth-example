package com.example.randomservice

import io.micrometer.observation.annotation.Observed
import org.springframework.stereotype.Service

@Service
@Observed(name = "random-service")
class RandomService {
    fun random(): Int {
        Thread.sleep(500)
        return (0..100).random()
    }
}