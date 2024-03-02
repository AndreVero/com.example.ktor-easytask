package com.example.services.goal

import com.example.dtos.mapper.toGoalResponse
import com.example.dtos.requests.GoalRequest
import com.example.dtos.response.GoalResponse
import com.example.entities.Goal
import com.example.entities.Goals
import org.jetbrains.exposed.sql.emptySized
import org.jetbrains.exposed.sql.transactions.transaction

class GoalServiceImpl : GoalService {

    override fun getGoals(): List<GoalResponse> {
        return transaction {
            val goalModels = Goal.all()
            goalModels.toList().map {
                it.toGoalResponse()
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
}