package com.example.database.model

import com.example.database.model.dto.GoalDto
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UserGoals : IntIdTable("user_goals") {
    val user = reference("user_id", Users)
    val goal = reference("goal_id", Goals)

    fun insert(token: String, goalId: Int) {
        transaction {
            val user = User.find { Users.token eq  token }.first()
            val goal = Goal.find { Goals.id eq goalId }.first()
            user.goals = SizedCollection(user.goals + goal)
        }
    }

    fun fetchUserGoals(token: String): List<GoalDto> {
        try {
            return transaction {
                val user = User.find { Users.token eq  token }.first()

                val goals = Goal.all()
                goals.toList().map {
                    GoalDto(
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