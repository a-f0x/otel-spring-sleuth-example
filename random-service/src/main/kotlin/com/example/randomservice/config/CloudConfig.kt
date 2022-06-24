package com.example.randomservice.config

import com.example.randomservice.dto.UserDTO
import org.slf4j.LoggerFactory
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Configuration
class CloudConfig(val notificationHandler: NotificationHandler) {

    @Bean
    fun cardUserEvent(): Consumer<UserDTO> =
        Consumer<UserDTO> { notification ->
            notificationHandler.onUserEvent(notification)
        }

}

@Component
class NotificationHandler {
    private val logger = LoggerFactory.getLogger(CloudConfig::class.java)

    @NewSpan("on user event receive")
    fun onUserEvent(dto: UserDTO) {
        logger.info("On user event: $dto")
        if (dto.firstName == "dlq")
            error("go to DLQ")
    }
}