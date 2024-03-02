package com.example.routing

import com.example.dtos.requests.CreateTaskRequest
import com.example.dtos.requests.DeleteTaskRequest
import com.example.dtos.requests.GetTasksRequest
import com.example.dtos.requests.UpdateTaskRequest
import com.example.services.task.TaskService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tasks(taskService: TaskService) {

    post("/tasks") {
        try {
            val request = call.receiveNullable<CreateTaskRequest>() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            taskService.createTask(createTaskRequest = request)
            call.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    get("/tasks") {
        try {
            val request = call.receiveNullable<GetTasksRequest>() ?: run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            call.respond(taskService.getTasks(request.goal_id))
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    authenticate {

        get("/current-tasks") {
            try {
                val userId = call.extractUserId() ?: run {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@get
                }

                call.respond(taskService.getCurrentTasks(userId))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }

        }

        post ("/update-task") {
            try {

                val request = call.receiveNullable<UpdateTaskRequest>() ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val userId = call.extractUserId() ?: run {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }

                call.respond(taskService.updateTask(request.task_id, userId))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        delete("/clear-tasks") {
            try {
                val request = call.receiveNullable<DeleteTaskRequest>() ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val userId = call.extractUserId() ?: run {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@delete
                }

                call.respond(taskService.clearTasks(userId, request.task_id))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}