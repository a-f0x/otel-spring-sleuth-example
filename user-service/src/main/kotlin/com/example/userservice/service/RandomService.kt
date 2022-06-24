package com.example.userservice.service

import com.example.userservice.dto.RandomDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RandomService(
    @Value("\${random-service.url}")
    private val randomServiceUrl: String,
    private val restTemplate: RestTemplate
) {
    fun getRandom(): RandomDTO = with("$randomServiceUrl/api/randomize") {
        restTemplate.exchange(
            this,
            HttpMethod.GET,
            HttpEntity(
                null,
                HttpHeaders(),
            ),
            RandomDTO::class.java
        ).body!!
    }
}

