package com.example.routing

import com.example.dtos.requests.AuthRequest
import com.example.dtos.requests.isValid
import com.example.dtos.response.AuthResponse
import com.example.services.auth.AuthService
import com.example.services.security.hashing.HashingService
import com.example.services.security.hashing.SaltedHash
import com.example.services.security.token.TokenClaim
import com.example.services.security.token.TokenConfig
import com.example.services.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    authService: AuthService
) {
    post("signup") {
        val request = call.receiveNullable<AuthRequest>() ?: run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (!request.isValid()) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        val saltedHash = hashingService.generateSaltedHash(request.password)
        try {
            authService.signUp(request, saltedHash)
            call.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.signIn(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    authService: AuthService
) {
    post("signin") {
        try {
            val request = call.receiveNullable<AuthRequest>() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val user = authService.findUser(request)

            if (user == null) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            val isValidPassword = hashingService.verify(
                value = request.password,
                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )
            if (!isValidPassword) {
                call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
                return@post
            }

            val token = tokenService.generate(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.id.value
                )
            )
            call.respond(
                status = HttpStatusCode.OK,
                message = AuthResponse(
                    token = token
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)

            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}

