package com.xojiakbar.taskmanager.fragments.settings_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.activities.LoginActivity
import com.xojiakbar.taskmanager.data.local.dao.TasksDao
import com.xojiakbar.taskmanager.data.local.dao.TaskscntDao
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import com.xojiakbar.taskmanager.data.local.database.AppDatabase
import com.xojiakbar.taskmanager.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding : FragmentSettingsBinding? = null
    private val binding get() = _binding
    var tasksCntDao : TaskscntDao? =null
    var tasksDao : TasksDao? =null
    var usersDao : UsersDao? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val appDatabase: AppDatabase = AppDatabase.getInstance(requireContext())
        tasksCntDao = appDatabase.tasksCntDao()
        tasksDao = appDatabase.taskDao()
        usersDao = appDatabase.userDao()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        binding?.layoutSettingsLanguage?.setOnClickListener {
            navController.navigate(R.id.changeLanguageFragment)
         }
        binding?.layoutChangeParol?.setOnClickListener {
            navController.navigate(R.id.changePasswordFragment)
        }
        binding?.layoutChangePin?.setOnClickListener {
            navController.navigate(R.id.changePinCodeFragment)
        }
        binding?.layoutLogOut?.setOnClickListener {
            val titleText = getString(R.string.wont_log_out)
                MaterialAlertDialogBuilder(requireContext())
                .setTitle(titleText)
                .setCancelable(false)
                .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
                .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                    dialog.dismiss()
                    logOut()
                }
                .show()
        }
    }
    fun logOut(){
        Preferences.clearPreferences()
        val intent = Intent(requireContext(),LoginActivity::class.java)
        usersDao?.deleteAll()
        tasksCntDao?.deleteAll()
        tasksDao?.deleteAll()
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.card_slide_out_left)
        requireActivity().finish()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}