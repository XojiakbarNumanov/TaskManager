package com.xojiakbar.taskmanager.fragments.dashboard_fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import com.xojiakbar.taskmanager.BuildConfig
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.activities.LoginActivity
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.data.local.dao.TasksDao
import com.xojiakbar.taskmanager.data.local.dao.TaskscntDao
import com.xojiakbar.taskmanager.data.local.dao.UsersDao
import com.xojiakbar.taskmanager.data.local.database.AppDatabase
import com.xojiakbar.taskmanager.databinding.FragmentDashboardBinding
import com.xojiakbar.taskmanager.fragments.login_fragment.LoginUIController
import com.xojiakbar.taskmanager.fragments.login_fragment.LoginViewModel
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.Date

class DashboardFragment : Fragment(), DashboardRouter {
    private var _binding: FragmentDashboardBinding? = null
    lateinit var toggle : ActionBarDrawerToggle
    private val binding get() = _binding!!
    private var viewModel: DashboardViewModel? = null
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val controller = DashboardUIController(requireContext())
        createToggle()
        controller.router = this
        binding.controller = controller
        loadingDialog = LoadingDialog.newInstance()

        val header = binding.navigationView.getHeaderView(0)
        val image = header.findViewById<CircleImageView>(R.id.image_of_user)
        val name = header.findViewById<TextView>(R.id._user_name)
        val status = header.findViewById<TextView>(R.id.status_name)
        name.text = Preferences.getUserFIO()
        status.text = Preferences.getUserRolesName()
        Picasso.get()
            .load("${BuildConfig.SERVER_URL}api/resources/" + Preferences.getImageResource() + "/view")
            .into(image)
        return binding.root
    }

    private fun createToggle() {
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController
        toggle = ActionBarDrawerToggle(requireActivity(),binding.drawerLayout,binding.toolbar,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toggle.drawerArrowDrawable.color = resources.getColor(R.color.white)
        requireActivity().actionBar?.setDisplayUseLogoEnabled(true)
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.nav_language->{
                    navController.navigate(R.id.changeLanguageFragment)

                }
                R.id.nav_change_pin->{
                    navController.navigate(R.id.changePinCodeFragment)
                }
                R.id.nav_change_pass->{
                    navController.navigate(R.id.changePasswordFragment)
                }
                R.id.nav_log_out->{
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
            true
        }
    }
    fun logOut(){
        Preferences.clearPreferences()
        val intent = Intent(requireContext(),LoginActivity::class.java)
        viewModel?.clearAllDb()
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.card_slide_out_left)
        requireActivity().finish()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_dashboard) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)
        viewModel!!.router = this
        Picasso.get()
            .load("${BuildConfig.SERVER_URL}api/resources/" + Preferences.getImageResource() + "/view")
            .into(binding?.userImage)
        if (Preferences.getIsFirstTime()) {
            refresh()
            Preferences.setIsFirstTime(false)
        }

        val navHostFragment2 = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController2 = navHostFragment2.navController
        binding.add.setOnClickListener {
                navController2.navigate(R.id.createTaskFragment)
             }
        if (Preferences.getUserTypesId()==6){
            binding.bottomNavigationView.menu.findItem(R.id.default_item).isVisible = false
            binding.bottomNavigationView.menu.findItem(R.id.tasksForAdminFragment).isVisible = false
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

    @SuppressLint("SimpleDateFormat")
    override fun refresh() {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val toDate = dateFormat.format(currentDate).toString()
        val fromDate = "01." + SimpleDateFormat("MM.yyyy").format(currentDate)
        viewModel?.getReportTasks(fromDate, toDate)
    }

    override fun setLoading(tag: String) {
        loadingDialog.show(requireActivity().supportFragmentManager, tag)

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