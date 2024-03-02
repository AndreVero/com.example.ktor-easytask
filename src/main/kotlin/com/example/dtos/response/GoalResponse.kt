package com.example.dtos.response

import kotlinx.serialization.Serializable

@Serializable
class GoalResponse(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val tasks: List<String>,
    val isActive: Boolean
)