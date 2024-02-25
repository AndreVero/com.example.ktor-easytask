package com.example

import com.example.plugins.*
import com.example.services.security.hashing.HashingService
import com.example.services.security.hashing.SHA256HashingService
import com.example.services.security.token.JwtTokenService
import com.example.services.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/easytask",
        driver = "org.postgresql.Driver",
        user = "postgres",
    )
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val tokenConfig = TokenConfig(
        issuer ="http://0.0.0.0:8080",
        audience = "users",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()
    val tokenService = JwtTokenService()

    configureSerialization()
    configureSecurity(tokenConfig = tokenConfig)
    configureRouting(
        hashingService = hashingService,
        tokenService = tokenService,
        tokenConfig = tokenConfig
    )
}
