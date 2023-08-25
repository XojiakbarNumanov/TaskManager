package com.xojiakbar.taskmanager.fragments.tasks_for_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.xojiakbar.taskmanager.BuildConfig
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.adapter.RecyclerAdapter
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.FragmentTasks2Binding
import com.xojiakbar.taskmanager.databinding.ItemTaskForAdminBinding
import com.xojiakbar.taskmanager.databinding.ItemTasksInfoBinding
import com.xojiakbar.taskmanager.fragments.tasks_for_admin.item.ItemUiController

private const val ARG_PARAM1 = "param1"


class TasksFragment : Fragment() ,TaskRouter{
    private var param: Int? = null
    private var _binding: FragmentTasks2Binding? = null
    private val binding get() = _binding!!
    private var adapter: RecyclerAdapter<TasksEntity?>? = null
    private lateinit var viewModel : TasksViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getInt(ARG_PARAM1)
        }
        viewModel = ViewModelProvider(this)[TasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTasks2Binding.inflate(inflater,container,false)
        adapter = RecyclerAdapter(R.layout.item_task_for_admin, getAdapterListener())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        initrRvList()
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    private fun initrRvList() {
                viewModel.getTasks(param!!).observe(viewLifecycleOwner) {
                    adapter!!.setList(it.toMutableList())
                    adapter!!.notifyDataSetChanged()
                }
    }

    private fun getAdapterListener(): RecyclerAdapter.AdapterListener<TasksEntity?> {
        return object : RecyclerAdapter.AdapterListener<TasksEntity?> {

            override fun setController(dataBinding: ViewDataBinding?) {
                val controller = ItemUiController(requireContext())
                controller.router = this@TasksFragment
                (dataBinding as ItemTaskForAdminBinding).controller = controller
            }

            override fun bindItem(
                dataBinding: ViewDataBinding?,
                item: TasksEntity?,
                position: Int?
            ) {
                (dataBinding as ItemTaskForAdminBinding).controller!!.setEntityInfo(item)
                Picasso.get().load(
                    BuildConfig.SERVER_URL +"api/resources/" + Preferences.getImageResource()
                        .toString() + "/view"
                ).into(dataBinding.userImage)

                dataBinding.executePendingBindings()
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param: Int) =
            TasksFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param)
                }
            }
    }

    override fun setLoading(title: String?) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(response: Any?) {
        TODO("Not yet implemented")
    }

    override fun onError(errorMsg: ErrorResult?) {
        TODO("Not yet implemented")
    }
}