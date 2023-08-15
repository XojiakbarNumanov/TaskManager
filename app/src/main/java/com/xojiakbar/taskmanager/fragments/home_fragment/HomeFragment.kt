package com.xojiakbar.taskmanager.fragments.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thanh.ha.piechart.PieItem
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.local.entity.TasksEntity
import com.xojiakbar.taskmanager.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), HomeRouter {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var viewModel: HomeViewModel? = null
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = listOf(
            PieItem(20f, resources.getColor(R.color.red)),
            PieItem(40f, resources.getColor(R.color.green)),
            PieItem(80f, resources.getColor(R.color.black)),
            PieItem(100f, resources.getColor(R.color.sunglow)),
            PieItem(120f, resources.getColor(R.color.orange))
            )

        binding.pieChart.submitList(list)
        binding.pieChart.animateProgress(0, 360)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun changeStatus(id: TasksEntity) {
    }

    override fun showDialog(task: TasksEntity) {
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