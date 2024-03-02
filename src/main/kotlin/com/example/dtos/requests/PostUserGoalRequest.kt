package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class PostUserGoalRequest(
    val id: Int
)