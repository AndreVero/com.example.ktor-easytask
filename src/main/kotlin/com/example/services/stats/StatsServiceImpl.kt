package com.example.services.stats

import com.example.dtos.mapper.toStatsResponse
import com.example.dtos.response.StatsResponse
import com.example.entities.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class StatsServiceImpl : StatsService {

    override fun createStats(goalId: Int, userId: Int) {
        transaction {
            Stat.new {
                progress = 0
                user_id = userId
                goal_id = goalId
            }
        }
    }

    override fun updateStats(
        goalId: Int,
        userId: Int,
        progressInt: Int
    ) {
        transaction {
            val user = User.find { Users.id eq userId }.first()
            val stats = Stat.find { (Stats.goal_id eq goalId) and (Stats.user_id eq user.id.value) }.first()

            stats.progress = progressInt
        }
    }

    override fun getStats(userId: Int): List<StatsResponse> {
        return transaction {
            val user = User.find { Users.id eq userId }.first()
            val goals = Goal.all()
            val statsModel = Stat.find { Stats.user_id eq user.id.value }
            statsModel.toList().map {
                it.toStatsResponse(goals)
            }
        }
    }
}