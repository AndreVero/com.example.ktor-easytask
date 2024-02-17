package com.example.database.model

import com.example.database.model.dto.UserDto
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object UserGoals : Table("user_goals") {
    val user = reference("user_id", Users)
    val goal = reference("goal_id", Goals)
    override val primaryKey = PrimaryKey(user, goal)

    fun insert() {
        transaction {

        }
    }

    fun fetchUsers(): List<UserDto> {
        try {
            return transaction {
                val userModels = User.all()
                userModels.toList().map {
                    UserDto(
                        id = it.id.value,
                        email = it.email,
                        goals = it.goals.map { goal -> goal.title }
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}