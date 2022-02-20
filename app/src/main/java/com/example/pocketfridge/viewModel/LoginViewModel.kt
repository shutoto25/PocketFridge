package com.example.pocketfridge.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pocketfridge.view.callback.Event

/**
 *
 */
class LoginViewModel : ViewModel() {
    private val _onLogin = MutableLiveData<Event<String>>()

    val onLogin: LiveData<Event<String>> get() = _onLogin

    fun onLogin() {
        _onLogin.value = Event("EVENT_LOGIN")
    }
}