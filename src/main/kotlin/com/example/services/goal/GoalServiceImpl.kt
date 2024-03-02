package com.example.services.goal

import com.example.dtos.mapper.toGoalResponse
import com.example.dtos.requests.GoalRequest
import com.example.dtos.requests.PostUserGoalRequest
import com.example.dtos.response.GoalResponse
import com.example.entities.Goal
import com.example.entities.Goals
import com.example.entities.User
import com.example.entities.Users
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction

class GoalServiceImpl : GoalService {

    override fun getGoals(): List<GoalResponse> {
        return transaction {
            val goalModels = Goal.all()
            goalModels.toList().map {
                it.toGoalResponse(false)
            }
        }
    }

    override fun postGoal(goalRequest: GoalRequest) {
        return transaction {
            Goal.new {
                title = goalRequest.title
                description = goalRequest.description
                icon = goalRequest.icon
            }
        }
    }

    override fun getUserGoals(userId: Int): List<GoalResponse> {
        return transaction {
            val user = User.find { Users.id eq userId }.first()

            val goals = Goal.all()
            goals.toList().map {
                it.toGoalResponse(isActive = user.goals.contains(it))
            }
        }
    }

    override fun postUserGoal(request: PostUserGoalRequest, userId: Int) {
        transaction {
            val user = User.find { Users.id eq userId }.first()
            val goal = Goal.find { Goals.id eq request.id }.first()
            user.goals = SizedCollection(user.goals.plus(goal))
        }
    }
}