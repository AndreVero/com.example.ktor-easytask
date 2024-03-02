package com.example.dtos

import com.example.dtos.response.GoalResponse
import kotlinx.serialization.Serializable

@Serializable
class StatsDto(
    val progress: Int,
    val goal: GoalResponse,
)
