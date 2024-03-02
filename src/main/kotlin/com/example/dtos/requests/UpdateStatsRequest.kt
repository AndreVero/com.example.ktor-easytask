package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
class UpdateStatsRequest(
    val goal_id: Int,
    val progress: Int
)