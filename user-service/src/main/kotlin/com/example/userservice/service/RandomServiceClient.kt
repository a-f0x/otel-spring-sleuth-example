package com.example.userservice.service

import com.example.userservice.dto.RandomDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "random-service-client", url = "\${random-service.url}")
interface RandomServiceClient {

    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/api/randomize"]
    )
    fun getRandom(): RandomDTO
}