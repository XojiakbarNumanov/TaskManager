package com.xojiakbar.taskmanager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment, val fragments: MutableList<Fragment>) :
    FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
        return fragments.size

    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}