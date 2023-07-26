package com.xojiakbar.taskmanager.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


abstract class BaseFragment<D : ViewDataBinding?, VM : BaseViewModel<*>> :
    Fragment() {
    var databinding: D? = null
        protected set
    protected var viewModel: VM? = null
//    protected var sweetDialog: BaseSweetDialog? = null

    @get:LayoutRes
    abstract val layoutId: Int
    abstract val viewModelClass: Class<VM>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        databinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewModel = ViewModelProvider(this).get(viewModelClass)
 //       sweetDialog = BaseSweetDialog(context)
        return databinding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.setRouter(null)
    }

    fun hideKeyboard() {
        val view = requireActivity().currentFocus
        if (view != null) {
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}