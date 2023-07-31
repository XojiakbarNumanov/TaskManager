package com.xojiakbar.taskmanager.fragments.change_languge_fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.databinding.FragmentChangeLanguageBinding


class ChangeLanguageFragment : Fragment() , ChangeLangugeRouter {
    private var _binding : FragmentChangeLanguageBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeLanguageBinding.inflate(inflater,container,false)
        val controller = ChangeLanguageUIController(requireActivity())
        controller.router = this
        binding?.controller = controller
        Toast.makeText(requireContext(), Preferences.getAppLanguage(), Toast.LENGTH_SHORT).show()
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun back() {
        Toast.makeText(requireContext(), Preferences.getAppLanguage(), Toast.LENGTH_SHORT).show()
    }
}