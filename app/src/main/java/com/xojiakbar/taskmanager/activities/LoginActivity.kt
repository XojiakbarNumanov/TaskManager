package com.xojiakbar.taskmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LanguageHalper
import com.xojiakbar.taskmanager.Utils.Preferences

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Preferences.init(applicationContext)
        LanguageHalper.setLanguageResourse(this)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_login) as NavHostFragment
        val navController = navHostFragment.navController
        supportFragmentManager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        if (Preferences.getIsFirst())
            navController.navigate(R.id.chooseLanguageFragment)
        else if (Preferences.getUserName() == "") {
            navController.popBackStack(R.id.loginFragment,true)
            navController.navigate(R.id.loginFragment)
        }
        else if (Preferences.getLocalPassword() == "") {
            navController.popBackStack(R.id.createPinCodeFragment,true)
            navController.navigate(R.id.createPinCodeFragment)
        }
        else {
            navController.popBackStack(R.id.passwordFragment,true)
            navController.navigate(R.id.passwordFragment)
        }

    }
}