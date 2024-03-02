package com.example.entities

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTasks : IntIdTable("user_tasks") {
    val user = reference("user_id", Users)
    val task = reference("task_id", Tasks)
}