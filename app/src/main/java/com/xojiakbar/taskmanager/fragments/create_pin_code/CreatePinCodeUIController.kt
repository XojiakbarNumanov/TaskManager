package com.xojiakbar.taskmanager.fragments.create_pin_code

import android.content.Context
import android.content.res.ColorStateList
import android.os.Vibrator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.BaseObservable
import com.google.android.material.textfield.TextInputLayout
import com.xojiakbar.taskmanager.R
import com.xojiakbar.taskmanager.Utils.Preferences
import java.util.Objects

class CreatePinCodeUIController(context: Context) : BaseObservable() {
    var router: CreatePinCodeRouter? = null
    private var context: Context? = null

    init {
        this.context = context
    }
    fun buttonOkClick(view: TextInputLayout, repeatView: TextInputLayout?) {
        router?.hideKeyboard()
        if (validator(view, repeatView!!)) {
            Preferences.setLocalPassword(view.editText!!.text.toString())
            router?.showDashboard()
        }
    }
    private fun validator(view: TextInputLayout, repeatView: TextInputLayout): Boolean {
        if (view.editText!!.text != null && view.editText!!.text.toString().trim { it <= ' ' }
                .isEmpty()) {
            view.error = context!!.getString(R.string.pin_code_is_required)
            view.setErrorTextColor(ColorStateList.valueOf(context!!.resources.getColor(R.color.red)))
            view.defaultHintTextColor =
                ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            shakeWithVibration(view)
            return false
        }
        if (view.editText!!.text != null &&
            view.editText!!.text.toString().length != 4
        ) {
            view.error = context!!.getString(R.string.pin_error)
            view.setErrorTextColor(ColorStateList.valueOf(context!!.resources.getColor(R.color.red)))
            view.defaultHintTextColor =
                ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            shakeWithVibration(view)
            return false
        }
        if (repeatView.editText!!.text != null &&
            repeatView.editText!!.text.toString().trim { it <= ' ' }.isEmpty()
        ) {
            repeatView.error = context!!.getString(R.string.pin_code_is_required)
            repeatView.setErrorTextColor(ColorStateList.valueOf(context!!.resources.getColor(R.color.red)))
            repeatView.defaultHintTextColor =
                ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            shakeWithVibration(repeatView)
            return false
        }
        if (view.editText!!.text.toString() != repeatView.editText!!.text.toString()) {
            repeatView.error = context!!.getString(R.string.pin_error)
            repeatView.setErrorTextColor(ColorStateList.valueOf(context!!.resources.getColor(R.color.red)))
            repeatView.defaultHintTextColor =
                ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            shakeWithVibration(repeatView)
            return false
        }
        return true
    }
    fun shakeWithVibration(view: View) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        view.startAnimation(shake)
        (Objects.requireNonNull(context!!.getSystemService(Context.VIBRATOR_SERVICE)) as Vibrator).vibrate(
            300
        )
    }
    fun onTextChanged(s: CharSequence?, view: TextInputLayout, repeatView: TextInputLayout) {
        if (view.hasFocus()) {
            if (s != null) {
                checkTextChange(s, view)
            }

        } else if (repeatView.hasFocus()) {
            if (view.editText!!.length() < 4 && view.error == null) {
                shakeWithVibration(view)
                view.error = context!!.getString(R.string.pin_error)
                view.setErrorTextColor(ColorStateList.valueOf(context!!.resources.getColor(R.color.red)))
                view.defaultHintTextColor =
                    ColorStateList.valueOf(context!!.resources.getColor(R.color.red))
            }
            if (s != null) {
                checkTextChange(s, repeatView)
            }
        }
    }
    private fun checkTextChange(s: CharSequence, view: TextInputLayout) {
        if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$".toRegex())) {
            if (view.editText!!.text.toString().trim { it <= ' ' } != "") {
                view.error = null
                view.setErrorTextColor(ColorStateList.valueOf(context!!.resources.getColor(R.color.black)))
                view.boxStrokeColor = context!!.resources.getColor(R.color.black)
                view.defaultHintTextColor =
                    ColorStateList.valueOf(context!!.resources.getColor(R.color.black))
            } else {
                view.error = null
                view.setErrorTextColor(ColorStateList.valueOf(context!!.resources.getColor(R.color.red)))
            }
        }
    }
}