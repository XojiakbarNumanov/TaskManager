package com.xojiakbar.taskmanager.fragments.forgot_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.databinding.FragmentForgotPasswordBinding

class ForgotPassFragment : Fragment() , ForgotPassRouter {
    private var _binding : FragmentForgotPasswordBinding? = null
    private val binding get() = _binding
    private var viewModel : ForgotPassViewModel? = null
    private var loadingDialog : LoadingDialog?= null
    private var navController : NavController? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ForgotPassViewModel::class.java]
        val controller = ForgotUIController(requireContext())
        controller.router = this
        binding?.controller = controller
        viewModel?.router = this
        loadingDialog = LoadingDialog.newInstance()
        navController = Navigation.findNavController(binding?.root!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun changeLogin(username: String, password: String) {
        viewModel?.forgotPassword(username,password)
    }

    override fun setLoading(tag :String) {
        loadingDialog?.show(requireFragmentManager(),tag)
    }

    override fun onSuccess(response: Any?) {
        try {
            loadingDialog?.dismiss()
        }
        catch (_: Exception){}
        navController?.popBackStack(R.id.loginFragment,false)
    }

    override fun onError(errorMsg: ErrorResult?) {
        try {
            loadingDialog?.dismiss()
        }
        catch (_: Exception){}
    }

}