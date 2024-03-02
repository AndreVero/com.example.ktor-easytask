package com.example.routing

import com.example.dtos.requests.GoalRequest
import com.example.dtos.requests.PostUserGoalRequest
import com.example.services.goal.GoalService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.goals(goalService: GoalService) {

    get("/goals") {
        try {
            call.respond(goalService.getGoals())
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest)
            e.printStackTrace()
        }
    }

    post("/goals") {
        val request = call.receiveNullable<GoalRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        try {
            goalService.postGoal(request)
            call.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest)
            e.printStackTrace()
        }
    }


    authenticate {
        get("/user-goals") {
            val userId = call.extractUserId() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            try {
                call.respond(goalService.getUserGoals(userId))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                e.printStackTrace()
            }
        }
    }

    authenticate {
        post("/user-goals") {
            val request = call.receiveNullable<PostUserGoalRequest>() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userId = call.extractUserId() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            try {
                call.respond(goalService.postUserGoal(request, userId))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                e.printStackTrace()
            }
        }
    }

}