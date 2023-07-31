package com.xojiakbar.taskmanager.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}