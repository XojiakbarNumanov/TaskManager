package com.xojiakbar.taskmanager.fragments.login_fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.LoadingDialog
import com.xojiakbar.taskmanager.api.result.ErrorResult
import com.xojiakbar.taskmanager.databinding.FragmentLoginBinding


class LoginFragment : Fragment() , LoginRouter {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var  viewModel : LoginViewModel ? =null
    lateinit var  navController :NavController
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val controller = LoginUIController(requireContext())
        controller.router = this
        binding.setController(controller)

        loadingDialog = LoadingDialog.newInstance()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel?.router = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        navController = Navigation.findNavController(binding.root)
        navController.popBackStack(R.id.loginFragment,false)

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

    override fun checkLogin(username: String?, password: String?, remember: Boolean) {
        viewModel?.login(username!!, password!!,remember)
    }

    override fun hideKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun setLoading() {
        loadingDialog.show(requireFragmentManager(),"Loading")
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onSuccess(response: Any?) {
            loadingDialog.dismiss()
            navController.navigate(R.id.createPinCodeFragment)
        }


    override fun onError(errorMsg: ErrorResult?) {
        loadingDialog.dismiss()
        Toast.makeText(requireContext(),errorMsg?.message , Toast.LENGTH_SHORT).show()
    }


}
