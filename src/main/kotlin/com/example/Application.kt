package com.example

import com.example.plugins.*
import com.example.services.auth.AuthService
import com.example.services.auth.AuthServiceImpl
import com.example.services.goal.GoalServiceImpl
import com.example.services.security.hashing.HashingService
import com.example.services.security.hashing.SHA256HashingService
import com.example.services.security.token.JwtTokenService
import com.example.services.security.token.TokenConfig
import com.example.services.stats.StatsServiceImpl
import com.example.services.task.TaskServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/easytask",
        driver = "org.postgresql.Driver",
        user = "postgres",
    )
    embeddedServer(Netty, port = 5353, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val tokenConfig = TokenConfig(
        issuer ="http://0.0.0.0:5353",
        audience = "users",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()
    val tokenService = JwtTokenService()
    val authService = AuthServiceImpl()
    val statsService = StatsServiceImpl()
    val taskService = TaskServiceImpl(statsService)
    val goalService = GoalServiceImpl(statsService, taskService)

    configureSerialization()
    configureSecurity(tokenConfig = tokenConfig)
    configureRouting(
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig,
        authService = authService,
        goalService = goalService,
        statsService = statsService,
        taskService = taskService
    )
    this.install(CORS){
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.ContentType)
        anyHost()
    }
}
