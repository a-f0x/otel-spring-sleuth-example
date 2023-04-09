package com.example.userservice

import com.example.userservice.repository.UserEntity
import io.micrometer.observation.annotation.Observed
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component


@Component
@Observed(
    name = "notification-service"
)
class NotificationService(private val streamBridge: StreamBridge) {
    private companion object {
        private const val TOPIC = "user-topic"
    }

    fun notify(person: UserEntity) {
        streamBridge.run { send(TOPIC, MessageBuilder.withPayload(person).build()) }
    }
}
