package com.example.dtos.response

import kotlinx.serialization.Serializable

@Serializable
data class GoalShortResponse(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val isActive: Boolean
)