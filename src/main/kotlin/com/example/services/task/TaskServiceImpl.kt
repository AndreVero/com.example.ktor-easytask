package com.example.services.task

import com.example.dtos.mapper.toTaskResponse
import com.example.dtos.requests.CreateTaskRequest
import com.example.dtos.response.TaskResponse
import com.example.entities.*
import com.example.services.stats.StatsService
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TaskServiceImpl(private val statsService: StatsService) : TaskService {

    override fun createTask(createTaskRequest: CreateTaskRequest) {
        transaction {
            Task.new {
                title = createTaskRequest.title
                description = createTaskRequest.description
                step = createTaskRequest.step
                progress = createTaskRequest.progress
                is_the_last_step = createTaskRequest.is_last_step
                goal = Goal.find { Goals.id eq createTaskRequest.goal_id }.first()
            }
        }

    }

    override fun getTasks(goalId: Int): List<TaskResponse> {
        return transaction {
            val goal = Goal.find { Goals.id eq goalId }.first()
            Task.find(Tasks.goal_id eq goalId).toList().map { task ->
                task.toTaskResponse(goal)
            }
        }
    }

    override fun getCurrentTasks(userId: Int): List<TaskResponse> {
        return transaction {
            User.find(Users.id eq userId).first().tasks.map {  task ->
                val goal = Goal.find(Goals.id eq task.goal.id).first()
                task.toTaskResponse(goal)
            }
        }
    }

    override fun updateTask(taskId: Int, userId: Int) {
        transaction {
            val user = User.find(Users.id eq userId).first()

            val currentTask = Task.find(Tasks.id eq taskId).first()

            val tasks = Task.find(Tasks.goal_id eq currentTask.goal.id).sortedBy { it.step }

            val nextTask = tasks.firstOrNull { currentTask.step < it.step }
            val mutableList = mutableListOf<Task>()
            mutableList.addAll(user.tasks)
            mutableList.remove(currentTask)
            nextTask?.let {
                mutableList.add(nextTask)
                statsService.updateStats(
                    goalId = nextTask.goal.id.value,
                    progressInt = currentTask.progress,
                    userId = userId
                )
            }

            user.tasks = SizedCollection(mutableList)
        }
    }

    override fun setFirstTask(userId: Int) {
        transaction {
            val user = User.find(Users.id eq userId).first()
            val task = Task.all().sortedBy { it.step }.first()

            user.tasks = SizedCollection(task) }
    }

    override fun clearTasks(userId: Int, taskId: Int) {
        transaction {
            val user = User.find(Users.id eq userId).first()
            val task = Task.find(Tasks.id eq taskId).first()

            statsService.updateStats(
                goalId = task.goal.id.value,
                userId = userId,
                progressInt = task.progress
            )
            user.tasks = SizedCollection(user.tasks.minus(task))
        }
    }

}