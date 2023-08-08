package com.xojiakbar.taskmanager.fragments.forgot_password

import android.content.Context
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputEditText
import com.xojiakbar.taskmanager.R

class ForgotUIController (context: Context) : BaseObservable() {
    var router : ForgotPassRouter? = null
    var context: Context
    init {
        this.context = context
    }



    fun signInButtonClick(login: TextInputEditText, password: TextInputEditText) {
        if (validateFields(login, password)!!) {
            if (login.text != null && password.text != null) changeLogin(
                login.text.toString(),
                password.text.toString(),
            )
        }
    }
    fun changeLogin(username: String, password: String) {
        router?.changeLogin(username, password)
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