package com.example.features.goals

import com.example.database.model.Goals
import com.example.database.model.dto.GoalDto

class GoalsController {

    fun fetchGoals() : List<GoalDto> {
        return Goals.fetchGoals()
    }

}