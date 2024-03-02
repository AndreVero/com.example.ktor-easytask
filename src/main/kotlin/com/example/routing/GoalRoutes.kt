package com.example.routing

import com.example.dtos.mapper.toGoalResponse
import com.example.dtos.requests.GoalRequest
import com.example.services.goal.GoalService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


//get("/userGoals") {
//    val principal = call.principal<JWTPrincipal>()
//    val userId = principal?.getClaim("userId", Int::class) ?: return@get
//    val goalsController = UserGoalsController()
//    call.respond(goalsController.fetchUserGoals(userId))
//}
//post ("/userGoals") {
//    val principal = call.principal<JWTPrincipal>()
//    val userId = principal?.getClaim("userId", Int::class) ?: return@post
//    val goalsController = UserGoalsController()
//    call.respond(goalsController.updateUserName(userId, 2))
//}

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
}