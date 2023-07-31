package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private  var _binding : FragmentDashboardBinding? = null
    private val  binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_dashboard) as NavHostFragment
        val navController = navHostFragment.navController
        navController.popBackStack()
        binding?.bottomNavigationView?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btn_home->{
                    navController?.navigate(R.id.homeFragment)
                }
                R.id.btn_tasks->{
                    navController?.navigate(R.id.homeFragment)
                }
                R.id.btn_statistics->{
                    navController?.navigate(R.id.statisticsFragment)
                }
                R.id.btn_settings->{
                    navController?.navigate(R.id.settingsFragment)
                }
            }
            true
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}