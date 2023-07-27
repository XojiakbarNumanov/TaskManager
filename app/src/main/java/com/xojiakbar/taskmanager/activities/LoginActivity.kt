package com.xojiakbar.taskmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.xojiakbar.taskmanager.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_login) as NavHostFragment
//        val navController = navHostFragment.navController
//        navController.navigate(R.id.chooseLanguageFragment)
    }
}