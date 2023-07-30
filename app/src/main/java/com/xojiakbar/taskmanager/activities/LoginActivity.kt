package com.xojiakbar.taskmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.NavHostFragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Preferences.init(applicationContext)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_login) as NavHostFragment
        val navController = navHostFragment.navController
        if (Preferences.getIsFirst())
            navController.navigate(R.id.chooseLanguageFragment)
        else if (Preferences.getUserName() == "")
            navController.navigate(R.id.loginFragment)
        else if (Preferences.getLocalPassword() == "")
            navController.navigate(R.id.createPinCodeFragment)
        else
            navController.navigate(R.id.passwordFragment)

    }
}