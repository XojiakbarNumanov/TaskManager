package com.xojiakbar.taskmanager.fragments.change_pin_code_fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.databinding.FragmentChangePinCodeBinding


class ChangePinCodeFragment : Fragment() ,ChangePinCodeRouter{

    private var _binding : FragmentChangePinCodeBinding? = null
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePinCodeBinding.inflate(inflater,container,false)
        val controller = ChangePinCodeUIController(requireContext())
        binding?.controller = controller
        controller.router = this
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBack() {
    }

    override fun onChange(passcode: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.change_pin_code)
            .setMessage(R.string.are_you_sure_change)
            .setCancelable(false)
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                Preferences.setLocalPassword(passcode!!)
            }
            .show()
    }
}