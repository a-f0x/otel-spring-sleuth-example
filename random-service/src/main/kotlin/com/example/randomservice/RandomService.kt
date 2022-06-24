package com.example.randomservice

import org.springframework.stereotype.Service

@Service
class RandomService {
    fun random(): Int {
        Thread.sleep(500)
        return (0..100).random()
    }
}