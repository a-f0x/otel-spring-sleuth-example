package com.example.userservice.service

import com.example.userservice.dto.RandomDTO
import org.springframework.stereotype.Service

@Service
class RandomService(
    private val randomServiceClient: RandomServiceClient
) {
    fun getRandom(): RandomDTO = randomServiceClient.getRandom()
}

