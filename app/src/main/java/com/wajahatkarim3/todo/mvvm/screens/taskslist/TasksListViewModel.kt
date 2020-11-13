package com.wajahatkarim3.todo.mvvm.screens.taskslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wajahatkarim3.todo.mvvm.model.TaskModel
import com.wajahatkarim3.todo.mvvm.repository.LocalRepository

class TasksListViewModel : ViewModel() {

    private val localRepository = LocalRepository

    val tasksListLiveData: LiveData<List<TaskModel>> = localRepository.tasksList

    private val _uiState = MutableLiveData<TasksListUI>()
    val uiState: LiveData<TasksListUI> = _uiState

    fun init() {
        updateUi()
    }

    fun addTask(title: String) {
        localRepository.addTask(title)
        updateUi()
    }

    fun markAsCompleted(taskModel: TaskModel) {
        var completedTask = taskModel.copy(completed = true)
        localRepository.updateTask(completedTask.id, completedTask)
        updateUi()
    }

    fun updateUi() {
        if (tasksListLiveData.value == null || tasksListLiveData.value?.size == 0) {
            _uiState.value = Empty
        } else {
            _uiState.value = TasksList
        }
    }
}