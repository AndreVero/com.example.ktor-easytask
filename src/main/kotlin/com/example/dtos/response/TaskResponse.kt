package com.example.dtos.response

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val id: Int,
    val title: String,
    val description: String,
    val progress: Int,
    val step: Int,
    val goal: GoalShortResponse,
    val is_the_last_step: Boolean,
)