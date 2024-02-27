package com.example.services.auth

import com.example.dtos.requests.AuthRequest
import com.example.entities.User
import com.example.entities.Users
import com.example.services.security.hashing.HashingService
import com.example.services.security.hashing.SaltedHash
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class AuthServiceImpl : AuthService {

    override fun findUser(request: AuthRequest): User? {
        val user = transaction { User.find(Users.username eq request.username).firstOrNull() }
        return user
    }

    override fun signUp(request: AuthRequest, saltedHash: SaltedHash) {
        transaction {
            User.new {
                username = request.username
                password = saltedHash.hash
                salt = saltedHash.salt
            }
        }
    }

}