package com.xojiakbar.taskmanager.fragments.choose_language_fragment

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.databinding.FragmentChooseLanguageBinding
import java.util.Locale


class ChooseLanguageFragment : Fragment() ,ChooseLanguageRouter{

    private var _binding: FragmentChooseLanguageBinding? = null
    private val binding get() = _binding
    private var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            binding
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChooseLanguageBinding.inflate(inflater, container, false)
        Preferences.init(requireContext())
        val controller = ChooseLanguageController(requireActivity())
        controller.router = this
        binding?.controller = controller
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showLoginFragment() {
        navController?.navigate(R.id.to_loginFragment)
    }

}

