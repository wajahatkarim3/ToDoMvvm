package com.wajahatkarim3.todo.mvvm.screens.taskslist

import android.animation.Animator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wajahatkarim3.todo.mvvm.R
import com.wajahatkarim3.todo.mvvm.databinding.ItemTaskLayoutBinding
import com.wajahatkarim3.todo.mvvm.model.TaskModel
import com.wajahatkarim3.todo.mvvm.util.showStrikeThrough

class TasksListAdapter : RecyclerView.Adapter<TasksListAdapter.TaskViewHolder> {

    private val tasksList = ArrayList<TaskModel>()
    var context: Context
    var onCompleted: (taskModel: TaskModel, position: Int) -> Unit = { taskModel, position ->  }
    var onClick: (taskModel: TaskModel) -> Unit = { taskModel ->  }

    constructor(context: Context, tasks: List<TaskModel>, onCompleted: (taskModel: TaskModel, position: Int) -> Unit, onClick: (taskModel: TaskModel) -> Unit) {
        this.context = context
        this.onCompleted = onCompleted
        this.onClick = onClick
        tasksList.clear()
        tasksList.addAll(tasks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var bi = ItemTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(bi)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasksList[position], position)
    }

    fun updateList(tasks: List<TaskModel>) {
        tasksList.clear()
        tasksList.addAll(tasks)
        notifyDataSetChanged()
    }

    fun updateItem(taskModel: TaskModel, position: Int) {
        tasksList[position] = taskModel
        notifyItemChanged(position)
    }

    inner class TaskViewHolder(val bi: ItemTaskLayoutBinding) : RecyclerView.ViewHolder(bi.root) {

        fun bind(taskModel: TaskModel, position: Int) {
            bi.apply {
                txtTaskTitle.setText(taskModel.title)
                if (taskModel.completed) {
                    lottieDone.visibility = View.GONE
                    btnDone.visibility = View.VISIBLE
                    btnDone.imageTintList = ColorStateList.valueOf(context.resources.getColor(R.color.white, null))
                    btnDone.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.colorCheckBackgroundDone, null))
                    txtTaskTitle.showStrikeThrough(true)
                }
                else {
                    lottieDone.visibility = View.GONE
                    btnDone.visibility = View.VISIBLE
                    btnDone.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.colorCheckBackground, null))
                }

                txtTaskTitle.setOnClickListener {
                    onClick.invoke(taskModel)
                }

                btnDone.setOnClickListener {
                    lottieDone.visibility = View.VISIBLE
                    lottieDone.addAnimatorUpdateListener {
                        if (it.animatedFraction >= 1f) {
                            // Animation Completed
                            lottieDone.visibility = View.GONE
                            btnDone.visibility = View.VISIBLE
                            btnDone.imageTintList = ColorStateList.valueOf(context.resources.getColor(R.color.white))
                            btnDone.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.colorCheckBackgroundDone, null))
                            onCompleted.invoke(taskModel, position)
                        }
                    }
                    lottieDone.playAnimation()
                }
            }
        }
    }

}