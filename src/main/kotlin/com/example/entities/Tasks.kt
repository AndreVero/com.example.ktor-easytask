package com.example.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Tasks : IntIdTable("tasks") {
    val title = Tasks.text("title")
    val description = Tasks.text("description")
    val goal_id = reference("goal_id", Goals)
}

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)
    var title by Tasks.title
    var description by Tasks.description
    var goal_id by Goal referencedOn Tasks.goal_id
}