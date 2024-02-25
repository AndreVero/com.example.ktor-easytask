package com.example.dtos

import kotlinx.serialization.Serializable

@Serializable
class StatsDto(
    val progress: Int,
    val goal: GoalDto,
)
