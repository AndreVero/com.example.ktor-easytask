package com.example.features.goals

import com.example.database.model.Stats
import com.example.database.model.dto.StatsDto

class StatsController {

    fun getStats(token: String): List<StatsDto> {
        return Stats.getStats(token)
    }

    fun updateStats(goalId: Int, token: String, progress: Int) {
        Stats.updateStats(goalId, token, progress)
    }

    fun createStats(goalId: Int, token: String) {
        Stats.createStats(goalId, token)
    }
}