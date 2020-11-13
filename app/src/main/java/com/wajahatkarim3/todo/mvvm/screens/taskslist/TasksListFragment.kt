package com.wajahatkarim3.todo.mvvm.screens.taskslist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethod
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wajahatkarim3.todo.mvvm.R
import com.wajahatkarim3.todo.mvvm.databinding.TasksListFragmentBinding

class TasksListFragment : Fragment() {

    companion object {
        fun newInstance() = TasksListFragment()
    }

    private lateinit var viewModel: TasksListViewModel
    private lateinit var bi: TasksListFragmentBinding
    lateinit var tasksListAdapter: TasksListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bi = TasksListFragmentBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TasksListViewModel::class.java)
        setupViews()
        initObservations()
        viewModel.init()
    }

    fun setupViews()
    {
        bi.txtNewTask.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE && bi.txtNewTask.text.toString().isNotEmpty()) {
                viewModel.addTask(bi.txtNewTask.text.toString())
                bi.txtNewTask.setText("")
                true
            }
            false
        }

        // RecyclerView
        context?.let {
            tasksListAdapter = TasksListAdapter(it, emptyList(),
                onCompleted = { taskModel, position ->
                    viewModel.markAsCompleted(taskModel)
                },
                onClick = {taskModel ->
                    var directions = TasksListFragmentDirections.actionTasksListFragmentToTaskDetailsFragment(taskModel)
                    findNavController().navigate(directions)
                })
            bi.recyclerTasks.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
            bi.recyclerTasks.adapter = tasksListAdapter
            tasksListAdapter.notifyDataSetChanged()
        }
    }

    fun initObservations() {
        viewModel.uiState.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                Empty -> {
                    bi.apply {
                        recyclerTasks.visibility = View.GONE
                        lottieEmptyList.visibility = View.VISIBLE
                        lblEmpty.visibility = View.VISIBLE
                    }
                }

                TasksList -> {
                    bi.apply {
                        recyclerTasks.visibility = View.VISIBLE
                        lottieEmptyList.visibility = View.GONE
                        lblEmpty.visibility = View.GONE
                    }
                }
            }
        })

        viewModel.tasksListLiveData.observe(viewLifecycleOwner, Observer {
            tasksListAdapter.updateList(it)
        })
    }

}