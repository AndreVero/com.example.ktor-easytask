package com.example.services.task

import com.example.dtos.requests.CreateTaskRequest
import com.example.dtos.response.TaskResponse

interface TaskService {

    fun createTask(createTaskRequest: CreateTaskRequest)

    fun getTasks(goalId: Int): List<TaskResponse>

    fun getCurrentTasks(userId: Int): List<TaskResponse>

    fun updateTask(taskId: Int, userId: Int)

    fun setFirstTask(userId: Int)

    fun clearTasks(userId: Int, taskId: Int)

}