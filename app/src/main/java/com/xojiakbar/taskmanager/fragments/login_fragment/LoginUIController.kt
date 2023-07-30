package com.xojiakbar.taskmanager.fragments.login_fragment

import android.content.Context
import android.view.View
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputEditText
import com.xojiakbar.taskmanager.R

class LoginUIController (context: Context) : BaseObservable() {
    var router : LoginRouter? = null
    private var visibility = 0
    var context: Context
    private var buttonTextId = 0
    init {
            this.visibility = View.INVISIBLE
            this.context = context
            buttonTextId = R.string.sign_in_btn
    }



    fun signInButtonClick(login: TextInputEditText, password: TextInputEditText) {
        router?.hideKeyboard()
        if (validateFields(login, password)!!) {
            if (login.text != null && password.text != null) checkLogin(
                login.text.toString(),
                password.text.toString(),
                false
            )
        }
    }
    fun checkLogin(username: String, password: String,remember :Boolean) {
        router?.checkLogin(username, password,remember)
    }
    private fun validateFields(login: TextInputEditText, password: TextInputEditText): Boolean? {
        if (login.text != null && login.text.toString().equals("")) {
            login.error = context.getString(R.string.input_login_please)
            return false
        }
        if (password.text != null && password.text.toString().isEmpty()) {
            password.error = context.getString(R.string.input_password_please)
            return false
        }
        if (checkCycrilic(password.text.toString())) {
            password.error = context.getString(R.string.cycrilic_error)
            return false
        }
        return true
    }
    private fun checkCycrilic(str: String): Boolean {
        for (c in str.toCharArray()) {
            if ((c < '0' || c > '9') && (c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) return true
        }
        return false
    }
}