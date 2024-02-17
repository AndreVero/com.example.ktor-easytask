package com.example.database.model.dto

import kotlinx.serialization.Serializable

@Serializable
class UserDto(
    val id: Int,
    val email: String,
    val goals: List<String>
)