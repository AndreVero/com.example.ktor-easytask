package com.example.entities

import com.example.dtos.StatsDto
import com.example.dtos.mapper.toGoalDto
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

object Stats : IntIdTable("stats") {
    val user_id = Stats.integer("user_id")
    val goal_id = Stats.integer("goal_id")
    val progress = Stats.integer("progress")

    fun updateStats(goalId: Int, userId: Int, progress: Int) {
        transaction {
            val user = User.find { Users.id eq userId }.first()
            val stats = Stat.find { (goal_id eq goalId) and (user_id eq user.id.value) }.first()

            stats.progress = progress
        }
    }

    fun createStats(goalId: Int, userId: Int) {
        transaction {
            val user = User.find { Users.id eq userId }.first()

            Stat.new {
                progress = 0
                user_id = user.id.value
                goal_id = goalId
            }
        }
    }

    fun getStats(userId: Int): List<StatsDto> {
        try {
            return transaction {
                val user = User.find { Users.id eq userId }.first()
                val goals = Goal.all()
                val statsModel = Stat.find { user_id eq user.id.value }
                statsModel.toList().map {
                    StatsDto(
                        progress = it.progress,
                        goal = goals.find { goal -> goal.id.value == it.goal_id }!!.toGoalDto()
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}

class Stat(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Stat>(Stats)
    var user_id by Stats.user_id
    var goal_id by Stats.goal_id
    var progress by Stats.progress
}