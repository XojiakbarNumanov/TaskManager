package com.xojiakbar.taskmanager.fragments.statics_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.adapter.RecyclerAdapter
import com.xojiakbar.taskmanager.data.local.entity.ReportTasksEntity
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.FragmentStatisticsBinding
import com.xojiakbar.taskmanager.databinding.ItemHomeRecyclerBinding
import com.xojiakbar.taskmanager.databinding.ItemStatisticsBinding
import com.xojiakbar.taskmanager.fragments.home_fragment.item.ItemUIController
import com.xojiakbar.taskmanager.fragments.statics_fragment.item.StatisticsItemUIController


class StatisticsFragment : Fragment() {
    private var _binding : FragmentStatisticsBinding? =null
    private val binding get() = _binding
    private var viewModel : StatisticsViewModel? = null
    private var adapter: RecyclerAdapter<ReportTasksEntity?>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater,container,false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[StatisticsViewModel::class.java]
        adapter = RecyclerAdapter(R.layout.item_statistics, getAdapterListener())
        viewModel?.getReportTasks()!!.observe(viewLifecycleOwner){
            binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())

            adapter!!.setList(it.toMutableList())
            binding?.recyclerView?.adapter = adapter
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun getAdapterListener(): RecyclerAdapter.AdapterListener<ReportTasksEntity?> {
        return object : RecyclerAdapter.AdapterListener<ReportTasksEntity?> {

            override fun setController(dataBinding: ViewDataBinding?) {
                val controller = StatisticsItemUIController(requireContext())
                (dataBinding as ItemStatisticsBinding).controller = controller


            }

            override fun bindItem(
                dataBinding: ViewDataBinding?,
                item: ReportTasksEntity?,
                position: Int?
            ) {
                (dataBinding as ItemStatisticsBinding).controller!!.setForEntity(item)
                Picasso.get().load("https://furorprogress.uz/api/resources/"+item?.img_resource_id+"/view").into(dataBinding.userImage)
                dataBinding.executePendingBindings()
            }
        }
    }

}