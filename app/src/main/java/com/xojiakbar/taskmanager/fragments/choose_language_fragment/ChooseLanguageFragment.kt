package com.xojiakbar.taskmanager.fragments.choose_language_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.databinding.FragmentChooseLanguageBinding


class ChooseLanguageFragment : Fragment() {

    private var _binding: FragmentChooseLanguageBinding? = null
    private val binding get() = _binding!!
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        binding.btnUz.setOnClickListener {
            Preferences.setAppLanguage("uz")
            navController.navigate(R.id.to_loginFragment)
            Preferences.setIsFirst(false)
        }
        binding.btnUk.setOnClickListener {
            Preferences.setAppLanguage("uk")
            navController.navigate(R.id.to_loginFragment)
            Preferences.setIsFirst(false)
        }
        binding.btnRu.setOnClickListener {
            Preferences.setAppLanguage("ru")
            navController.navigate(R.id.to_loginFragment)
            Preferences.setIsFirst(false)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}