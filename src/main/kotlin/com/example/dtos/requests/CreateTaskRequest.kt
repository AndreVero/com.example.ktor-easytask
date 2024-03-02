package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateTaskRequest(
    val title: String,
    val description: String,
    val progress: Int,
    val step: Int,
    val goal_id: Int,
    val is_last_step: Boolean,
)