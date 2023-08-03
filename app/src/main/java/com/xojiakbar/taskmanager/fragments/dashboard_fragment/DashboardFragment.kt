package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.databinding.FragmentDashboardBinding
import com.xojiakbar.taskmanager.fragments.login_fragment.LoginUIController
import com.xojiakbar.taskmanager.fragments.login_fragment.LoginViewModel

class DashboardFragment : Fragment() ,DashboardRouter{
    private  var _binding : FragmentDashboardBinding? = null
    private val  binding get() = _binding
    private var  viewModel : DashboardViewModel? =null
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        val controller = DashboardUIController(requireContext())
        controller?.router = this
        binding?.controller = controller
        loadingDialog = LoadingDialog.newInstance()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_dashboard) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(binding?.bottomNavigationView!!,navController)
        viewModel!!.router = this

    }
    override fun onResume() {
        super.onResume()
        viewModel!!.router = this
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun refresh() {
        viewModel?.getTasksCnt(Preferences.getUserId())
    }

    override fun setLoading() {
        loadingDialog.show(requireActivity().supportFragmentManager,"Loading")
    }

    override fun onSuccess(response: Any?) {
            loadingDialog.dismiss()

    }

    override fun onError(errorMsg: ErrorResult?) {
        //loadingDialog.dismiss()
    }

}