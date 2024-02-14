package com.example.database.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object Goals : IntIdTable("goals") {
    val title = Goals.text("title")
    val description = Goals.text("description")
    val icon = Goals.text("icon")

    fun insert(goalDto: GoalDto) {
        transaction {
            Goals.insert {
                it[id] = goalDto.id
                it[title] = goalDto.title
                it[description] = goalDto.description
                it[icon] = goalDto.icon
            }
        }
    }

    fun fetchGoals(): List<GoalDto> {
        try {
            return transaction {
                val goalModels = Goal.all()
                goalModels.toList().map {
                    GoalDto(
                        id = it.id.value,
                        title = it.title,
                        description = it.description,
                        icon = it.icon,
                        tasks = it.tasks.map { task -> task.title }
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }

    }
}

class Goal(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Goal>(Goals)
    var title by Goals.title
    var description by Goals.description
    var icon by Goals.icon
    val tasks by Task referrersOn Tasks.goal_id
}


