package com.example.userservice.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CommonConfiguration {
    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().apply {
        registerKotlinModule()
        registerModule(
            JavaTimeModule()
        )
    }

//    /**
//     * Что бы работало распространение контектса рест темплейт должен быть доступен как бин
//     * */
//    @Bean
//    fun restTemplate(objectMapper: ObjectMapper): RestTemplate = RestTemplateBuilder()
//        .additionalMessageConverters(MappingJackson2HttpMessageConverter(objectMapper))
//        .requestFactory(HttpComponentsClientHttpRequestFactory::class.java)
//        .build()
}