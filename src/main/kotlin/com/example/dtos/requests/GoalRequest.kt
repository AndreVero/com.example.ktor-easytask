package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
class GoalRequest(
    val title: String,
    val description: String,
    val icon: String
)