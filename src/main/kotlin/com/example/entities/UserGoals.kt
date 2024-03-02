package com.example.entities

import com.example.dtos.response.GoalResponse
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UserGoals : IntIdTable("user_goals") {
    val user = reference("user_id", Users)
    val goal = reference("goal_id", Goals)

    fun insert(userId: Int, goalId: Int) {
        transaction {
            val user = User.find { Users.id eq userId }.first()
            val goal = Goal.find { Goals.id eq goalId }.first()
            user.goals = SizedCollection(user.goals + goal)
        }
    }

    fun fetchUserGoals(userId: Int): List<GoalResponse> {
        try {
            return transaction {
                val user = User.find { Users.id eq userId }.first()

                val goals = Goal.all()
                goals.toList().map {
                    GoalResponse(
                        id = it.id.value,
                        description = it.description,
                        title = it.title,
                        isActive = user.goals.contains(it),
                        icon = it.icon,
                        tasks = it.tasks.map { task -> task.title }
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}