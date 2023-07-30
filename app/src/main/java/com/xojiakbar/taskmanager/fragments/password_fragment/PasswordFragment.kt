package com.xojiakbar.taskmanager.fragments.password_fragment

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.activities.MainActivity
import com.xojiakbar.taskmanager.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() , PasswordRouter {
    private  var _binding : FragmentPasswordBinding?  = null
    private  val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordBinding.inflate(inflater,container,false)
        val controller = PasswordUIController(requireContext())
        controller.router = this
        binding.setController(controller)
        Preferences.init(requireContext())
        return binding.root
    }

    override fun showDashboard() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.card_slide_out_left)
        requireActivity().finish()
    }

}