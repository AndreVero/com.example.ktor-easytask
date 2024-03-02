package com.example.dtos.mapper

import com.example.entities.Goal
import com.example.dtos.response.GoalResponse
import com.example.dtos.response.GoalShortResponse

fun Goal.toGoalResponse(isActive: Boolean) : GoalResponse {
    return GoalResponse(
        id = this.id.value,
        title = this.title,
        description = this.description,
        icon = this.icon,
        tasks = this.tasks.map { task -> task.title },
        isActive = isActive
    )
}

fun Goal.toShortGoalResponse(isActive: Boolean) : GoalShortResponse {
    return GoalShortResponse(
        id = this.id.value,
        title = this.title,
        description = this.description,
        icon = this.icon,
        isActive = isActive
    )
}