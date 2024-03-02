package com.example.dtos.response

import kotlinx.serialization.Serializable

@Serializable
class StatsResponse(
    val progress: Int,
    val goal: GoalResponse,
)