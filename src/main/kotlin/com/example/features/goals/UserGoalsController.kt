package com.example.features.goals

import com.example.entities.UserGoals
import com.example.dtos.GoalDto


class UserGoalsController {

    fun fetchUserGoals(userId: Int) : List<GoalDto> {
        return UserGoals.fetchUserGoals(userId)
    }

    fun updateUserName(userId: Int, goalId: Int) {
        return UserGoals.insert(userId, goalId)
    }

}