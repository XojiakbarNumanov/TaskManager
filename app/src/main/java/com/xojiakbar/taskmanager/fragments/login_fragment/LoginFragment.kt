package com.xojiakbar.taskmanager.fragments.login_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.databinding.FragmentLoginBinding
import com.xojiakbar.taskmanager.ui.base.BaseFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class LoginFragment(
    override val layoutId: Int,
    override val viewModelClass: Class<LoginViewModel>
) : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    private var param1: Int? = null
    private var param2: Class<LoginViewModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getSerializable(ARG_PARAM2) as Class<LoginViewModel>?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


        fun newInstance(param1: String, param2: String) =
            LoginFragment(param1,param2).apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
}
