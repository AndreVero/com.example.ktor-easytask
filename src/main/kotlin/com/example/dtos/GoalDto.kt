package com.example.dtos

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

@Serializable
class GoalDto(
    val id: Int,
    val title: String,
    val description: String,
    val icon: String,
    val tasks: List<String>,
    val isActive: Boolean
)