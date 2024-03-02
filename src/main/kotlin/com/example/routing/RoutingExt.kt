package com.example.routing

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun ApplicationCall.extractUserId(): Int? {
    val principal = this.principal<JWTPrincipal>()
    return principal?.getClaim("userId", Int::class)
}
