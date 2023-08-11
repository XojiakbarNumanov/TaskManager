package com.xojiakbar.taskmanager.fragments.home_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.adapter.RecyclerAdapter
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Row
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.FragmentHomeBinding
import com.xojiakbar.taskmanager.databinding.ItemHomeRecyclerBinding
import com.xojiakbar.taskmanager.fragments.home_fragment.dialog.SendInspectionDialog
import com.xojiakbar.taskmanager.fragments.home_fragment.item.ItemUIController


class HomeFragment : Fragment(), HomeRouter {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private var viewModel: HomeViewModel? = null
    private var adapter: RecyclerAdapter<TasksEntity?>? = null
    private var list: MutableList<TasksEntity> = ArrayList()
    private var listfiltered: MutableList<TasksEntity> = ArrayList()
    var statusId = -1
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        val controller = HomeUIController(requireContext())
        loadingDialog = LoadingDialog.newInstance()
        list = ArrayList()
        binding?.controller = controller
        viewModel?.getTasks()?.observe(viewLifecycleOwner) {
            if (it != null) {
                controller.listentity = it
            }
            binding?.controller = controller
        }
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecyclerAdapter(R.layout.item_home_recycler, getAdapterListener())
        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.spinnerArray,
            R.layout.spinner_dropdown_item
        )
        binding?.spinner?.adapter = spinnerAdapter
        viewModel?.getTasks()!!.observe(viewLifecycleOwner) {
            list = it
            if (statusId == -1) {
                adapter!!.setList(list.toMutableList())
                adapter!!.notifyDataSetChanged()
            }
        }
        viewModel?.getTasksById(statusId)!!.observe(viewLifecycleOwner) {
            filter(statusId)
        }
        binding?.spinner?.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                statusId = position
                if (statusId == 0)
                    statusId = -1
                filter(statusId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }


    }

    fun filter(statusId: Int) {
        viewModel?.getTasks()?.observe(viewLifecycleOwner){
            list = it
        }
        listfiltered.clear()

        for (i in list) {
            if (i.task_statuses_id == statusId)
                listfiltered.add(i)
        }
        if (statusId == -1) {
            adapter!!.setList(list.toMutableList())
            adapter!!.notifyDataSetChanged()

        } else {
            adapter!!.setList(listfiltered.toMutableList())
            adapter!!.notifyDataSetChanged()
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
                Picasso.get().load(
                    "https://furorprogress.uz/api/resources/" + Preferences.getImageResource()
                        .toString() + "/view"
                ).into(dataBinding.userImage)

                dataBinding.executePendingBindings()
            }
        }
    }

    override fun changeStatus(it: TasksEntity) {
        var statusId = -1
        var titleText = ""
        when {
            it.task_statuses_id == 4 -> {
                statusId = 5
                titleText = getString(R.string.change_status_pause)
            }

            it.task_statuses_id == 3 || it.task_statuses_id == 5 -> {
                statusId = 4
                titleText = getString(R.string.change_status_process)

            }

            it.task_statuses_id == 2 || it.task_statuses_id == 7 -> {
                statusId = 3
                titleText = getString(R.string.change_status_accept)

            }
        }
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(titleText)
            .setCancelable(false)
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                changeStatuses(it, statusId)
            }
            .show()


    }

    override fun showDialog(task: TasksEntity) {

            val row  = Row(
                task.id,
                6,
                task.task_code,
                task.projects_name,
                task.task_priorities_name,
                task.name,
                task.created_date,
                task.curr_executer_name,
                task.task_priorities_id,
                task.process_time,
                "",
                task.task_statuses_name,
                null
            )
            val dialog = SendInspectionDialog(row)
            dialog.isCancelable = false
            dialog.show(requireFragmentManager(),"")

    }

    fun changeStatuses(it: TasksEntity, statusId: Int) {


        val task = Row(
            it.id,
            statusId,
            it.task_code,
            it.projects_name,
            it.task_priorities_name,
            it.name,
            it.created_date,
            it.curr_executer_name,
            it.task_priorities_id,
            it.process_time,
            "",
            it.task_statuses_name,
            null
        )
        viewModel?.updateTaskStatus(task)
        if (binding?.spinner?.selectedItemPosition == 0)
            this.statusId = -1
        filter(this.statusId)
    }

    override fun setLoading(tag: String) {
        loadingDialog.show(requireFragmentManager(), tag)
    }

    override fun onSuccess(response: Any?) {
        try {
            loadingDialog.dismiss()
        } catch (_: Exception) {
        }
    }

    override fun onError(errorMsg: ErrorResult?) {
        try {
            loadingDialog.dismiss()
        } catch (_: Exception) {
        }

    }

}