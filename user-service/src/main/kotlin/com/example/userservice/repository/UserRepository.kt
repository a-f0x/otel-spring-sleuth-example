package com.example.userservice.repository

import org.springframework.cloud.sleuth.annotation.NewSpan
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Int> {
    @NewSpan("repository layer")
    fun getAllByFirstName(firstName: String): List<UserEntity>
}