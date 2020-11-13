package com.wajahatkarim3.todo.mvvm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskModel (
        val id: Long,
        val title: String,
        val description: String,
        val dueDate: Long,
        val completed: Boolean
) : Parcelable