package com.xojiakbar.taskmanager.fragments.tasks_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.adapter.ViewPagerAdapter
import com.xojiakbar.taskmanager.databinding.FragmentTasksBinding


class TasksFragment : Fragment() {


    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private val listOfFragment: MutableList<Fragment> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        initParam()
        binding.viewPager.adapter = viewPagerAdapter
        changePosition()

        return binding.root
    }

    private fun changePosition() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getStringArray(R.array.spinnerArray)[position]
        }.attach()
    }

    fun initParam(){
        loadFragments()
        viewPagerAdapter = ViewPagerAdapter(this,listOfFragment)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun loadFragments() {
        listOfFragment.add(Tasks.newInstance(-1))
        listOfFragment.add(Tasks.newInstance(3))
        listOfFragment.add(Tasks.newInstance(5))
        listOfFragment.add(Tasks.newInstance(4))
        listOfFragment.add(Tasks.newInstance(7))
    }


}