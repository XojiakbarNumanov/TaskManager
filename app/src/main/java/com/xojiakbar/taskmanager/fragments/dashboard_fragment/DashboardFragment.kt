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
import com.squareup.picasso.Picasso
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.databinding.FragmentDashboardBinding
import com.xojiakbar.taskmanager.fragments.login_fragment.LoginUIController
import com.xojiakbar.taskmanager.fragments.login_fragment.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Date

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
        Picasso.get().load("https://furorprogress.uz/api/resources/"+Preferences.getImageResource()+"/view").into(binding?.userImage)
        if (Preferences.getIsFirstTime())
        {
            refresh()
            Preferences.setIsFirstTime(false)
        }
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
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val toDate = dateFormat.format(currentDate).toString()
        val fromDate = "01."+ SimpleDateFormat("MM.yyyy").format(currentDate)
        viewModel?.getReportTasks(fromDate,toDate)
    }

    override fun setLoading(tag :String) {
        loadingDialog.show(requireActivity().supportFragmentManager,tag)

    }

    override fun onSuccess(response: Any?) {
        try {
            loadingDialog.dismiss()
        }
        catch (_: Exception){}

    }

    override fun onError(errorMsg: ErrorResult?) {
        try {
            loadingDialog.dismiss()
        }
        catch (_: Exception){}
    }

}