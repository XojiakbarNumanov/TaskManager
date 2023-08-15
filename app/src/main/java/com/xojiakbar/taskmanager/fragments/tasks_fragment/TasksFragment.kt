package com.xojiakbar.taskmanager.fragments.tasks_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
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
        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.setCurrentItem(tab!!.position)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        binding.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayout.getTabAt(position)?.select()
            }
        })
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