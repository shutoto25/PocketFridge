package com.example.pocketfridge.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketfridge.model.repsitory.UserLoginRepository
import com.example.pocketfridge.view.callback.Event
import kotlinx.coroutines.launch

/**
 * ログインViewModel.
 */
class UserLoginViewModel : ViewModel() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "UserLoginViewModel"
    }

    /** Firebaseリポジトリ. */
    private val repository = UserLoginRepository.instance

    /** ログイン状態イベント. */
    private val _onSign = MutableLiveData<Event<String>>()
    val onSign: LiveData<Event<String>> get() = _onSign

    /** 入力パラメータ(email/address). */
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    /** SignIn/SignUp切り替えフラグ. */
    val isSignIn = MutableLiveData(true)

    /** 入力値が有効か. */
    private fun isValid() = email.value != null && password.value != null

    /** ログイン済みか. */
    fun isLogin() = repository.isLogin()

    /** ログイン済みの場合は強制的に画面遷移. */
    fun onAlreadyLogin() {
        _onSign.value = Event("EVENT_SIGN_IN")
    }

    /** SignIn/SignUp切り替えフラグ変更. */
    fun changeHowToLogin() {
        isSignIn.value = !isSignIn.value!!
    }

    /** 新規アカウント作成. */
    fun onSignUp() = viewModelScope.launch {
        Log.d(TAG, "onSignUp() called")
        val eventResult =
            if (isValid()) repository.onSignUp(email.value!!, password.value!!) else "EVENT_ERROR"
        _onSign.value = Event(eventResult)
    }

    /** ログイン. */
    fun onSignIn() = viewModelScope.launch {
        Log.d(TAG, "onSignIn() called")
        val eventResult =
            if (isValid()) repository.onSignIn(email.value!!, password.value!!) else "EVENT_ERROR"
        _onSign.value = Event(eventResult)
    }

    /** googleでのログイン. */
    fun firebaseAuthWithGoogle(idToken: String) = viewModelScope.launch {
        val eventResult = repository.firebaseAuthWithGoogle(idToken)
        _onSign.value = Event(eventResult)
    }

}