package com.xojiakbar.taskmanager.fragments.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.adapter.RecyclerAdapter
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Row
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.FragmentHomeBinding
import com.xojiakbar.taskmanager.databinding.ItemHomeRecyclerBinding
import com.xojiakbar.taskmanager.fragments.home_fragment.item.ItemUIController


class HomeFragment : Fragment(),HomeRouter {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding
    private var viewModel : HomeViewModel? =null

    private var adapter: RecyclerAdapter<TasksEntity?>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val controller = HomeUIController(requireContext())
        binding?.controller = controller
        viewModel?.getTasksCnt()?.observe(viewLifecycleOwner){
            controller.entity = it
            binding?.controller = controller
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapter(R.layout.item_home_recycler, getAdapterListener())
        viewModel?.getTasks()!!.observe(viewLifecycleOwner){
            binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
            adapter!!.setList(it.toMutableList())
            binding?.recyclerView?.adapter = adapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun getAdapterListener(): RecyclerAdapter.AdapterListener<TasksEntity?> {
        return object : RecyclerAdapter.AdapterListener<TasksEntity?> {

            override fun setController(dataBinding: ViewDataBinding?) {
                val controller = ItemUIController(requireContext())
                controller.router = this@HomeFragment
                (dataBinding as ItemHomeRecyclerBinding).controller = controller


            }

            override fun bindItem(
                dataBinding: ViewDataBinding?,
                item: TasksEntity?,
                position: Int?
            ) {
                (dataBinding as ItemHomeRecyclerBinding).controller!!.setEntity99(item)
                dataBinding.executePendingBindings()
            }
        }
    }

    override fun changeStatus(id: Int?) {
        viewModel?.getByID(id!!)!!.observe(viewLifecycleOwner){

            val task = Row(
            it.id,
            it.task_statuses_id,
            it.task_code,
            it.projects_name,
            it.task_priorities_name,
            it.name,
            it.created_date,
            it.curr_executer_name,
            it.task_priorities_id,
            it.process_time,
            it.status_description
            )
            Toast.makeText(requireContext(), task.id.toString(), Toast.LENGTH_SHORT).show()
            viewModel?.updateTaskStatus(task)
        }
    }

    override fun setLoading() {
    }

    override fun onSuccess(response: Any?) {
        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
    }

    override fun onError(errorMsg: ErrorResult?) {
        Toast.makeText(requireContext(), errorMsg?.message, Toast.LENGTH_SHORT).show()

    }

}