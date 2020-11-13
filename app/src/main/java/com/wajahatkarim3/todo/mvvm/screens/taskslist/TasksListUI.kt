package com.wajahatkarim3.todo.mvvm.screens.taskslist

sealed class TasksListUI

object Loading : TasksListUI()
object Empty : TasksListUI()
object TasksList : TasksListUI()