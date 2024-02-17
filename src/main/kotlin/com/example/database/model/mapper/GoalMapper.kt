package com.example.database.model.mapper

import com.example.database.model.Goal
import com.example.database.model.dto.GoalDto

fun Goal.toGoalDto() : GoalDto {
    return GoalDto(
        id = this.id.value,
        title = this.title,
        description = this.description,
        icon = this.icon,
        tasks = this.tasks.map { task -> task.title },
        isActive = false
    )
}