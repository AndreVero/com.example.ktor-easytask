package com.example.services.goal

import com.example.dtos.requests.GoalRequest
import com.example.dtos.requests.PostUserGoalRequest
import com.example.dtos.response.GoalResponse

interface GoalService {

    fun getGoals() : List<GoalResponse>

    fun postGoal(goalRequest: GoalRequest)

    fun getUserGoals(userId: Int) : List<GoalResponse>

    fun postUserGoal(request: PostUserGoalRequest, userId: Int)

}