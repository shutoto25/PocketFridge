package com.example.pocketfridge.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pocketfridge.model.data.Group
import com.example.pocketfridge.model.repsitory.GroupRepository
import com.example.pocketfridge.utility.PrefUtil
import com.example.pocketfridge.view.callback.Event
import kotlinx.coroutines.launch

/**
 *
 */
class FridgeLoginViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "FridgeLoginViewModel"
    }

    private val repository = GroupRepository.instance

    /** 状態イベント. */
    private val _onAccessDB = MutableLiveData<Event<String>>()
    val onAccessDB: LiveData<Event<String>> get() = _onAccessDB

    /** 入力パラメータ(name/password). */
    val fridgeName = MutableLiveData<String>()
    val fridgePassword = MutableLiveData<String>()

    /** login. */
    fun onNewFridge() = viewModelScope.launch {
        Log.d(TAG, "onNewFridge() called")

        if(fridgeName.value == null || fridgePassword.value == null) {
            return@launch
        }

        try {
            val response = repository.post(Group(fridgeName.value!!, fridgePassword.value!!))
            if (response.isSuccessful) {
                response.body()?.let {
                    val pref = PrefUtil(getApplication())
                    pref.put(PrefUtil.MY_FRIDGE_ID, it.groupId)
                    pref.put(PrefUtil.MY_FRIDGE_NAME, fridgeName.value!!)
                    pref.put(PrefUtil.MY_FRIDGE_PASSWORD, fridgePassword.value!!)
                }
                // 画面遷移.
                _onAccessDB.value = Event("EVENT_NEW_FRIDGE")
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}