package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskRequest(
    val task_id: Int
)