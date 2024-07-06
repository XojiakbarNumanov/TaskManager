package com.xojiakbar.taskmanager.fragments.tasks_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.adapter.RecyclerAdapter
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.beans.task_bean.Task
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.ItemTaskInfoBinding
import com.xojiakbar.taskmanager.databinding.RecyclerTasksBinding
import com.xojiakbar.taskmanager.databinding.TestBinding
import com.xojiakbar.taskmanager.fragments.home_fragment.dialog.SendInspectionDialog
import com.xojiakbar.taskmanager.fragments.tasks_fragment.item.ItemUiController


private const val ARG_PARAM1 = "param1"

class Tasks : Fragment(), TasksRouter {
    private var statusId: Int? = null
    private var _binding: RecyclerTasksBinding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerAdapter<TasksEntity?>? = null
    private lateinit var viewModel: TasksViewModel
    private var loadingDialog: LoadingDialog = LoadingDialog()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            statusId = it.getInt(ARG_PARAM1)
        }
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecyclerTasksBinding.inflate(inflater, container, false)
        adapter = RecyclerAdapter(R.layout.test, getAdapterListener())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        initrRvList()
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initrRvList() {
        if (statusId == -1) {
            viewModel.getTasks().observe(viewLifecycleOwner) {
                adapter!!.setList(it.toMutableList())
                adapter!!.notifyDataSetChanged()
            }
        } else {
            viewModel.gettasksById(statusId!!).observe(viewLifecycleOwner) {
                adapter?.setList(it.toMutableList())
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param: Int) =
            Tasks().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param)
                }
            }
    }

    private fun getAdapterListener(): RecyclerAdapter.AdapterListener<TasksEntity?> {
        return object : RecyclerAdapter.AdapterListener<TasksEntity?> {

            override fun setController(dataBinding: ViewDataBinding?) {
                val controller = ItemUiController(requireContext())
                controller.router = this@Tasks
                (dataBinding as TestBinding).controller = controller
            }

            override fun bindItem(
                dataBinding: ViewDataBinding?,
                item: TasksEntity?,
                position: Int?
            ) {
                (dataBinding as TestBinding).controller!!.setEntityInfo(item)
//                Picasso.get().load(
//                    BuildConfig.SERVER_URL +"api/resources/" + Preferences.getImageResource()
//                        .toString() + "/view"
//                ).into(dataBinding.userImage)
                dataBinding.cardView.setOnClickListener {
                    // SwipeLayout ni o'ng tomondan yechib o'tqazish uchun kodni yozing
                    val settleDestX = -dataBinding.swipeLayout.dragDistance
                    dataBinding.swipeLayout.viewDragHelper.smoothSlideViewTo(dataBinding.swipeLayout.contentView!!, settleDestX, 0)
                    ViewCompat.postInvalidateOnAnimation(dataBinding.swipeLayout)
                }
                dataBinding.executePendingBindings()
            }
        }
    }

    override fun changeStatus(task: TasksEntity) {
        var statusId = -1
        var titleText = ""
        when {
            task.task_statuses_id == 4 -> {
                statusId = 5
                titleText = getString(R.string.change_status_pause)
            }

            task.task_statuses_id == 3 || task.task_statuses_id == 5 -> {
                statusId = 4
                titleText = getString(R.string.change_status_process)

            }

            task.task_statuses_id == 2 || task.task_statuses_id == 7 -> {
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
                changeStatuses(task, statusId)
            }
            .show()
    }

    fun changeStatuses(it: TasksEntity, statusId: Int) {

        val task: Task = Task()
        task.id = it.id
        task.task_statuses_id = statusId
        task.task_code = it.task_code
        task.projects_name = it.projects_name
        task.task_priorities_name = it.task_priorities_name
        task.name = it.name
        task.created_date = it.created_date
        task.curr_executor_name = it.curr_executor_name
        task.task_priorities_id = it.task_priorities_id
        task.process_time = it.process_time
        task.description = ""
        task.task_statuses_name = it.task_statuses_name

        viewModel.updateTaskStatus(task)
        initrRvList()
    }

    override fun showDialog(it: TasksEntity) {
        val task = Task()
        task.id = it.id
        task.task_statuses_id = 6
        task.task_code = it.task_code
        task.projects_name = it.projects_name
        task.task_priorities_name = it.task_priorities_name
        task.name = it.name
        task.created_date = it.created_date
        task.curr_executor_name = it.curr_executor_name
        task.task_priorities_id = it.task_priorities_id
        task.process_time = it.process_time
        task.description = ""
        task.task_statuses_name = it.task_statuses_name
        val dialog = SendInspectionDialog(task)
        dialog.isCancelable = false
        dialog.show(requireFragmentManager(), "")
    }

    override fun setLoading(title: String?) {
        loadingDialog.show(requireFragmentManager(), title)
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