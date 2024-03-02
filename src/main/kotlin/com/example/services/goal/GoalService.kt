package com.example.services.goal

import com.example.dtos.requests.GoalRequest
import com.example.dtos.response.GoalResponse
import com.example.entities.Goal

interface GoalService {

    fun getGoals() : List<GoalResponse>

    fun postGoal(goalRequest: GoalRequest)

}