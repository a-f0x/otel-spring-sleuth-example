package com.example.userservice.service

import com.example.userservice.NotificationService
import com.example.userservice.dto.UserDTO
import com.example.userservice.repository.UserEntity
import com.example.userservice.repository.UserRepository
import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.stereotype.Service

@Service
class UserService(
    private val notificationService: NotificationService,
    private val userRepository: UserRepository
) {
    @NewSpan("user service layer span")
    fun createPerson(userDTO: UserDTO): Int {
        if (userDTO.firstName == "error") error("Invalid name")
        val e = userRepository.save(
            UserEntity(
                id = null,
                firstName = userDTO.firstName,
                lastName = userDTO.lastName
            )
        )
        notificationService.notify(e)
        return e.id!!

    }

    fun search(firstName: String): List<UserEntity> = userRepository.getAllByFirstName(firstName)
}