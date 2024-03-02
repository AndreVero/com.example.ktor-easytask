package com.example.dtos.mapper

import com.example.dtos.response.StatsResponse
import com.example.entities.Goal
import com.example.entities.Stat
import org.jetbrains.exposed.sql.SizedIterable

fun Stat.toStatsResponse(goals: SizedIterable<Goal>) : StatsResponse {
    return StatsResponse(
        progress = this.progress,
        goal = goals.find { goal -> goal.id.value == this.goal_id }!!.toGoalResponse(false)
    )
}