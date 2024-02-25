package com.example.plugins

import com.example.features.goals.GoalsController
import com.example.features.goals.StatsController
import com.example.features.goals.UserGoalsController
import com.example.routing.authenticate
import com.example.routing.getSecretInfo
import com.example.routing.signIn
import com.example.routing.signUp
import com.example.services.security.hashing.HashingService
import com.example.services.security.token.TokenConfig
import com.example.services.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        signIn(hashingService, tokenService, tokenConfig)
        signUp(hashingService)
        authenticate()
        getSecretInfo()

        get("/") {
            call.respondText("Hello World!")
        }
        get("/goals") {
            val goalsController = GoalsController()
            call.respond(goalsController.fetchGoals())
        }
        get("/userGoals") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class) ?: return@get
            val goalsController = UserGoalsController()
            call.respond(goalsController.fetchUserGoals(userId))
        }
        post ("/userGoals") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class) ?: return@post
            val goalsController = UserGoalsController()
            call.respond(goalsController.updateUserName(userId, 2))
        }
        get("/stats") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class) ?: return@get
            val goalsController = StatsController()
            call.respond(goalsController.getStats(userId))
        }
        post("/stats") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class) ?: return@post
            val goalsController = StatsController()
            call.respond(goalsController.createStats(2,userId))
        }
        put ("/stats") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class) ?: return@put
            val goalsController = StatsController()
            call.respond(goalsController.updateStats(2, userId, 60))
        }
    }
}
