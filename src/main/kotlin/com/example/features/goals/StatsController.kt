package com.example.features.goals

import com.example.entities.Stats
import com.example.dtos.StatsDto

class StatsController {

    fun getStats(userId: Int): List<StatsDto> {
        return Stats.getStats(userId)
    }

    fun updateStats(goalId: Int, userId: Int, progress: Int) {
        Stats.updateStats(goalId, userId, progress)
    }

    fun createStats(goalId: Int, userId: Int) {
        Stats.createStats(goalId, userId)
    }
}