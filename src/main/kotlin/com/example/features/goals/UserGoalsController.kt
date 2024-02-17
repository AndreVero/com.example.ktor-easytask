package com.example.features.goals

import com.example.database.model.UserGoals
import com.example.database.model.dto.GoalDto


class UserGoalsController {

    fun fetchUserGoals(token: String) : List<GoalDto> {
        return UserGoals.fetchUserGoals(token)
    }

    fun updateUserName(token: String, goalId: Int) {
        return UserGoals.insert(token, goalId)
    }

}