package com.example.dtos.mapper

import com.example.dtos.response.TaskResponse
import com.example.entities.Goal
import com.example.entities.Task

fun Task.toTaskResponse(goal: Goal) : TaskResponse {
    return TaskResponse(
        id = this.id.value,
        title = this.title,
        description = this.description,
        goal = goal.toShortGoalResponse(isActive = true),
        progress = this.progress,
        step = this.step,
        is_the_last_step = this.is_the_last_step
    )
}