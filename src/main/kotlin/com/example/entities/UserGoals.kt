package com.example.entities

import org.jetbrains.exposed.dao.id.IntIdTable

object UserGoals : IntIdTable("user_goals") {
    val user = reference("user_id", Users)
    val goal = reference("goal_id", Goals)
}