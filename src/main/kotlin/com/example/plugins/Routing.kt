package com.example.plugins

import com.example.features.goals.GoalsController
import com.example.features.goals.StatsController
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
            call.respond(goalsController.fetchUserGoals("12345"))
        }
        post ("/userGoals") {
            val goalsController = UserGoalsController()
            call.respond(goalsController.updateUserName("12345", 2))
        }
        get("/stats") {
            val goalsController = StatsController()
            call.respond(goalsController.getStats("12345"))
        }
        post("/stats") {
            val goalsController = StatsController()
            call.respond(goalsController.createStats(2,"12345"))
        }
        put ("/stats") {
            val goalsController = StatsController()
            call.respond(goalsController.updateStats(2, "12345", 60))
        }
    }
}
