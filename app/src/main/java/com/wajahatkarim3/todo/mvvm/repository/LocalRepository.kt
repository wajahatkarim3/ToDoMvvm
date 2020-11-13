package com.wajahatkarim3.todo.mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wajahatkarim3.todo.mvvm.model.TaskModel

object LocalRepository {

    private val _tasksList = MutableLiveData<List<TaskModel>>()
    val tasksList: LiveData<List<TaskModel>> = _tasksList

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
        var tempList = _tasksList.value as? MutableList
        if (tempList == null) tempList = arrayListOf()
        tempList.add(task)
        _tasksList.value = tempList
    }

    fun updateTask(id: Long, task: TaskModel) {
        var index = _tasksList.value?.indexOfFirst { id == it.id } ?: -1

        if (index != -1) {
            var tempList = _tasksList.value as? MutableList
            if (tempList == null) tempList = arrayListOf()
            tempList.set(index, task)
            _tasksList.value = tempList
        }
    }
}