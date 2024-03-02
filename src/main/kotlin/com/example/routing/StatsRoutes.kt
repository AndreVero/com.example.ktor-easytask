package com.example.routing

import com.example.dtos.requests.UpdateStatsRequest
import com.example.services.stats.StatsService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.stats(statsService: StatsService) {

    authenticate {

        get("/stats") {
            try {
                val userId = call.extractUserId() ?: run {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@get
                }
                call.respond(statsService.getStats(userId))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }

        }

        put ("/stats") {
            try {
                val request = call.receiveNullable<UpdateStatsRequest>() ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val userId = call.extractUserId() ?: run {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@put
                }
                statsService.updateStats(
                    request.goal_id,
                    userId,
                    request.progress
                )
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}