package com.example.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username: String,
    val password: String
)

fun AuthRequest.isValid() : Boolean {
    return when {
        username.isBlank() || password.isBlank() -> false
        password.length < 8 -> false
        else -> true
    }
}