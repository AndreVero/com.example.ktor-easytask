package com.example.database.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Goal : Table("goals") {
    private val uuid = Goal.char("uuid", 20)
    private val title = Goal.char("title", 50)

    fun insert(goalDto: GoalDto) {
        transaction {
            Goal.insert {
                it[uuid] = goalDto.uuid
                it[title] = goalDto.title
            }
        }
    }

    fun fetchGoals(): List<GoalDto> {
        try {
            val goalModels = Goal.selectAll()
            return goalModels.map {
                GoalDto(
                    uuid = it[uuid],
                    title = it[title]
                )
            }
        } catch (e: Exception) {
            return emptyList()
        }

    }
}


