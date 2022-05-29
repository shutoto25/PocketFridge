package com.example.pocketfridge.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pocketfridge.view.callback.Event

class CameraViewModel (application: Application) : AndroidViewModel(application) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientViewModel"
    }

    /** 画面遷移イベント */
    private val _onEvent = MutableLiveData<Event<String>>()
    val onEvent: LiveData<Event<String>> get() = _onEvent

    /** 画面遷移イベント更新. */
    fun takePic() {
        Log.d(TAG, "takePic() called")
        _onEvent.value = Event("TAKE_PIC")
    }
}