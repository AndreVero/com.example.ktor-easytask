package com.example.plugins

import com.example.routing.*
import com.example.services.auth.AuthService
import com.example.services.goal.GoalService
import com.example.services.security.hashing.HashingService
import com.example.services.security.token.TokenConfig
import com.example.services.security.token.TokenService
import com.example.services.stats.StatsService
import com.example.services.task.TaskService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    authService: AuthService,
    goalService: GoalService,
    statsService: StatsService,
    taskService: TaskService
) {
    routing {
        signIn(hashingService, tokenService, tokenConfig, authService)
        signUp(hashingService, authService)
        authenticate()
        getSecretInfo()
        goals(goalService)
        stats(statsService)
        tasks(taskService)
    }
}
