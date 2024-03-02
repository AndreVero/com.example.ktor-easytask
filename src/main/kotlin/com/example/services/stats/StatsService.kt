package com.example.services.stats

import com.example.dtos.response.StatsResponse

interface StatsService {

    fun createStats(goalId: Int, userId: Int)

    fun updateStats(goalId: Int, userId: Int, progressInt: Int)

    fun getStats(userId: Int): List<StatsResponse>

}