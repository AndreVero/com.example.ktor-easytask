package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetTasksRequest(
    val goal_id: Int,
)