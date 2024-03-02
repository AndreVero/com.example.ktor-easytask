package com.example.dtos.mapper

import com.example.entities.Goal
import com.example.dtos.response.GoalResponse

fun Goal.toGoalResponse() : GoalResponse {
    return GoalResponse(
        id = this.id.value,
        title = this.title,
        description = this.description,
        icon = this.icon,
        tasks = this.tasks.map { task -> task.title },
        isActive = false
    )
}