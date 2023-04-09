package com.example.randomservice.config

import com.example.randomservice.dto.UserDTO
import io.micrometer.observation.annotation.Observed
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Configuration
class CloudConfig(val notificationHandler: NotificationHandler) {

    @Bean
    fun onUserEvent(): Consumer<UserDTO> =
        Consumer<UserDTO> { notification ->
            notificationHandler.onUserEvent(notification)
        }

}

@Component
@Observed(
    name = "notification-handler"
)

class NotificationHandler {
    private val logger = LoggerFactory.getLogger(CloudConfig::class.java)

    fun onUserEvent(dto: UserDTO) {
        logger.info("On user event: $dto")
        if (dto.firstName == "dlq")
            error("go to DLQ")
        userProcess(dto)
    }

    private fun userProcess(dto: UserDTO) {
        Thread.sleep(300)
        return
    }
}