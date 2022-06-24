package com.example.userservice

import com.example.userservice.repository.UserEntity
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.cloud.sleuth.annotation.SpanTag
import org.springframework.cloud.sleuth.annotation.TagValueResolver
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component


@Component
class NotificationService(private val streamBridge: StreamBridge) {
    private companion object {
        private const val TOPIC = "user-topic"
    }

    @NewSpan(TOPIC)
    fun notify(@SpanTag("user", resolver = UserEntityTagValueResolver::class) person: UserEntity) {
        streamBridge.run { send(TOPIC, MessageBuilder.withPayload(person).build()) }
    }
}

@Component
class UserEntityTagValueResolver : TagValueResolver {
    override fun resolve(parameter: Any?): String = with(parameter as UserEntity) {
        "$firstName $lastName"
    }
}
