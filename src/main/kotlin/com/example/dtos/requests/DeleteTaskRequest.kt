package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class DeleteTaskRequest(
    val task_id: Int,
)