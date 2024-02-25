package com.example.features.goals

import com.example.entities.Goals
import com.example.dtos.GoalDto

class GoalsController {

    fun fetchGoals() : List<GoalDto> {
        return Goals.fetchGoals()
    }

}