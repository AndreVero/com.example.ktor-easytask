package com.example.features.goals

import com.example.database.model.Goal
import com.example.database.model.GoalDto

class GoalsController {

    fun fetchGoals() : List<GoalDto> {
        return Goal.fetchGoals()
    }

}