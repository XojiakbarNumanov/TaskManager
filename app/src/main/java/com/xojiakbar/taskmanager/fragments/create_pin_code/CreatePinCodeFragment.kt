package com.xojiakbar.taskmanager.fragments.create_pin_code

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.activities.MainActivity
import com.xojiakbar.taskmanager.databinding.FragmentCreatePinCodeBinding

class CreatePinCodeFragment : Fragment() , CreatePinCodeRouter{

    private var _binding: FragmentCreatePinCodeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Preferences.init(requireContext())
        _binding = FragmentCreatePinCodeBinding.inflate(inflater,container,false)
        val controller = CreatePinCodeUIController(requireContext())
        controller.router = this
        binding.setController(controller)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showDashboard() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.card_slide_out_left)
        requireActivity().finish()
    }

    override fun hideKeyboard() {
        if (activity != null) {
            val view = requireActivity().currentFocus
            if (view != null) {
                val imm =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }


}