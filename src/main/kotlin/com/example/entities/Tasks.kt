package com.example.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Tasks : IntIdTable("tasks") {
    val title = Tasks.text("title")
    val description = Tasks.text("description")
    val progress = Tasks.integer("progress")
    val step = Tasks.integer("step")
    val is_the_last_step = Tasks.bool("is_last_step")
    val goal_id = reference("goal_id", Goals)
}

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)
    var title by Tasks.title
    var description by Tasks.description
    var progress by Tasks.progress
    var step by Tasks.step
    var is_the_last_step by Tasks.is_the_last_step
    var goal by Goal referencedOn Tasks.goal_id
}