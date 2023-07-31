package com.xojiakbar.taskmanager.fragments.change_password_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : Fragment(), ChangePasswordRouter {

    private var _binding : FragmentChangePasswordBinding? = null
    private val binding get() = _binding
    private var viewModel : ChangePasswordViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(inflater,container,false)
        Preferences.init(requireContext())
        val controller = ChangePasswordUIController(requireContext())
        controller.router = this
        binding?.controller = controller
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangePasswordViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel?.router  = this

    }
    override fun onBack() {
        TODO("Not yet implemented")
    }

    override fun changePassword(newPassword: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.change_password)
            .setMessage(R.string.are_you_sure_change)
            .setCancelable(false)
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which -> dialog.dismiss() }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                dialog.dismiss()
                viewModel!!.changeUserPassword(newPassword)
            }
            .show()
    }

    override fun setLoading(b: Boolean) {
        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(response: Any?) {
        Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()
    }

    override fun onError(errorMsg: ErrorResult?) {
        Toast.makeText(requireContext(), errorMsg?.message, Toast.LENGTH_SHORT).show()
    }

}