package com.example.database.model.dto

import kotlinx.serialization.Serializable

@Serializable
class StatsDto(
    val progress: Int,
    val goal: GoalDto,
)
