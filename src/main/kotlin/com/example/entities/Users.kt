package com.example.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("users") {
    val password = Users.varchar("password", 64)
    val username = Users.varchar("username", 60)
    val salt = Users.varchar("salt", 100)
}

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    var password by Users.password
    var username by Users.username
    var salt by Users.salt
    var goals by Goal via UserGoals
}