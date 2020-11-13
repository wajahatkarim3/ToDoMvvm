package com.wajahatkarim3.todo.mvvm.repository

import com.wajahatkarim3.todo.mvvm.model.TaskModel

object LocalRepository {

    private val _tasksList = mutableListOf<TaskModel>()
    val tasksList: List<TaskModel> = _tasksList

    fun addTask(title: String) {
        var taskModel = TaskModel(
                id = System.currentTimeMillis(),
                title = title,
                completed = false,
                description = "",
                dueDate = 0L
        )
        addTask(taskModel)
    }

    fun addTask(task: TaskModel) {
        _tasksList.add(task)
    }

    fun updateTask(id: Long, task: TaskModel) {
        var index = _tasksList.indexOfFirst { id == it.id }

        if (index != -1) {
            _tasksList[index] = task
        }
    }

    fun removeTask(id: Long) {
        var index = _tasksList.indexOfFirst { id == it.id }

        if (index != -1) {
            _tasksList.removeAt(index)
        }
    }
}