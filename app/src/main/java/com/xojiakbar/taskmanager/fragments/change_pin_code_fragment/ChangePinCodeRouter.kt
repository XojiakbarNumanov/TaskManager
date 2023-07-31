package com.xojiakbar.taskmanager.fragments.change_pin_code_fragment

interface ChangePinCodeRouter {
    fun onBack()

    fun onChange(passcode: String?)
}