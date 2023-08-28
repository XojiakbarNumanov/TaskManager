package com.xojiakbar.taskmanager.fragments.tasks_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.adapter.ViewPagerAdapter
import com.xojiakbar.taskmanager.databinding.FragmentTasksBinding
import okhttp3.internal.notifyAll
import kotlin.isInitialized as isInitialized


class TasksFragment : Fragment() {
    var tabLayoutMediator: TabLayoutMediator?=null

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private var listOfFragment: MutableList<Fragment> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        initParam()

        binding.viewPager.adapter = viewPagerAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayoutMediator = TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getStringArray(R.array.tablayout_array)[position]
        }
        tabLayoutMediator?.attach()

    }
    fun initParam() {
        loadFragments()
        viewPagerAdapter = ViewPagerAdapter(this, listOfFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun loadFragments() {
        listOfFragment = arrayListOf(
        Tasks.newInstance(-1),
        Tasks.newInstance(2),
        Tasks.newInstance(3),
        Tasks.newInstance(5),
        Tasks.newInstance(4),
        Tasks.newInstance(7),
        Tasks.newInstance(6)
        )
    }
}