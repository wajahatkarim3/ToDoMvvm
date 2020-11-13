package com.wajahatkarim3.todo.mvvm.model

data class TaskModel (
        val id: Long,
        val title: String,
        val description: String,
        val dueDate: Long,
        val completed: Boolean
)