package com.example.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Goals : IntIdTable("goals") {
    val title = Goals.text("title")
    val description = Goals.text("description")
    val icon = Goals.text("icon")
}

class Goal(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Goal>(Goals)
    var title by Goals.title
    var description by Goals.description
    var icon by Goals.icon
    val tasks by Task referrersOn Tasks.goal_id
}




