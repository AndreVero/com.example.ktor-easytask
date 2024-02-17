package com.example.database.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Stats : IntIdTable("stats") {
    val user_id = Stats.integer("user_id")
    val goal_id = Stats.integer("goal_id")
    val progress = Stats.integer("progress")
}

class Stat(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Stat>(Stats)
    var user_id by Stats.user_id
    var goal_id by Stats.goal_id
    var progress by Stats.progress
}