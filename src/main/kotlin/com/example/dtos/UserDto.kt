package com.example.dtos

import kotlinx.serialization.Serializable

@Serializable
class UserDto(
    val id: Int,
    val username: String,
    val goals: List<String>,
)