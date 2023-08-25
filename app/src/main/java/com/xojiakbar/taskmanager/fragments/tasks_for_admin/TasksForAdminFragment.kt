package com.xojiakbar.taskmanager.fragments.tasks_for_admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.adapter.ViewPagerAdapter
import com.xojiakbar.taskmanager.databinding.FragmentTasksForAdminBinding


class TasksForAdminFragment : Fragment() {
    private var _binding : FragmentTasksForAdminBinding? = null
    private val binding get() = _binding!!
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val listOfFragment: MutableList<Fragment> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksForAdminBinding.inflate(inflater,container,false)
        initParam()
        binding.viewPager.adapter = viewPagerAdapter
        changePosition()
        return binding.root
    }
    private fun changePosition() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getStringArray(R.array.tablayout_array_admin)[position]
        }.attach()
    }
    fun initParam(){
        loadFragments()
        viewPagerAdapter = ViewPagerAdapter(this,listOfFragment)
    }
    fun loadFragments() {
        listOfFragment.add(TasksFragment.newInstance(0))
        listOfFragment.add(TasksFragment.newInstance(1))
        listOfFragment.add(TasksFragment.newInstance(2))
        listOfFragment.add(TasksFragment.newInstance(3))
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}