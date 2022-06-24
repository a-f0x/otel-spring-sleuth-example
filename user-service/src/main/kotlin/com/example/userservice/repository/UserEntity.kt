package com.example.userservice.repository

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("USERS")
data class UserEntity(
    @Id
    val id: Int?,
    val firstName: String,
    val lastName: String
)