package com.example.features.goals

import com.example.database.model.UserGoals
import com.example.database.model.dto.UserDto


class UserGoalsController {

    fun fetchUserGoals() : List<UserDto> {
        return UserGoals.fetchUsers()
    }

}