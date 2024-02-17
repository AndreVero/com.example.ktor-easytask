package com.example.plugins

import com.example.features.goals.GoalsController
import com.example.features.goals.UserGoalsController
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/goals") {
            val goalsController = GoalsController()
            call.respond(goalsController.fetchGoals())
        }
        get("/userGoals") {
            val goalsController = UserGoalsController()
            call.respond(goalsController.fetchUserGoals())
        }
    }
}
