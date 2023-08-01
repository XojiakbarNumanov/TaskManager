package com.xojiakbar.taskmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.NavHostFragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LanguageHalper
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding :ActivityMainBinding? =null
    private val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        Preferences.init(applicationContext)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentDestination?.id != R.id.dashboardFragment)
                    navController.popBackStack(R.id.dashboardFragment,false)
                else
                finish()
            }
        })
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}