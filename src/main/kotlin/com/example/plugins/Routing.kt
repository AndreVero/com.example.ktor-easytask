package com.example.plugins

import com.example.features.goals.StatsController
import com.example.routing.*
import com.example.services.auth.AuthService
import com.example.services.goal.GoalService
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
    tokenConfig: TokenConfig,
    authService: AuthService,
    goalService: GoalService
) {
    routing {
        signIn(hashingService, tokenService, tokenConfig, authService)
        signUp(hashingService, authService)
        authenticate()
        getSecretInfo()
        goals(goalService)

        get("/") {
            call.respondText("Hello World!")
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
