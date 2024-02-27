package com.example.services.auth

import com.example.dtos.requests.AuthRequest
import com.example.entities.User
import com.example.services.security.hashing.SaltedHash

interface AuthService {

    fun findUser(request: AuthRequest): User?

    fun signUp(request: AuthRequest, saltedHash: SaltedHash)

}