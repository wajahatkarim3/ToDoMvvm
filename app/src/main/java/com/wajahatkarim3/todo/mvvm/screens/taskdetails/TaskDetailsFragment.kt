package com.wajahatkarim3.todo.mvvm.screens.taskdetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.wajahatkarim3.todo.mvvm.R
import com.wajahatkarim3.todo.mvvm.databinding.TaskDetailsFragmentBinding

class TaskDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TaskDetailsFragment()
    }

    private lateinit var viewModel: TaskDetailsViewModel
    private lateinit var bi: TaskDetailsFragmentBinding
    val args: TaskDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bi = TaskDetailsFragmentBinding.inflate(inflater, container, false)
        return bi.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskDetailsViewModel::class.java)
        viewModel.taskModel = args.taskModel

        setupViews()
    }

    fun setupViews() {
        bi.txtTitle.setText(viewModel.taskModel.title)
    }

}