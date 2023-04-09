package com.example.userservice.repository

import io.micrometer.observation.annotation.Observed
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Int> {
    @Observed(
        contextualName = "repository layer"
    )
    fun getAllByFirstName(firstName: String): List<UserEntity>
}