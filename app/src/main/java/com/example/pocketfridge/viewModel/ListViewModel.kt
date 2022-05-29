package com.example.pocketfridge.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.model.repsitory.UserLoginRepository
import com.example.pocketfridge.model.response.IngredientResponse
import com.example.pocketfridge.utility.PrefUtil
import com.example.pocketfridge.view.callback.Event
import kotlinx.coroutines.launch

/**
 * ListFragmentに紐づけて、リストが更新したときにアプリ内に保持しているリストも？更新する
 * 更新したときにListFragment側のObserverが反応して更新が走るてきな？LiveData
 */
class ListViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "ListViewModel"
    }

    /** 全データリスト. */
    var ingredientList: List<Ingredient>? = null

    var ingredient: Ingredient? = null

    /** 画面遷移イベント */
    private val _onTransit = MutableLiveData<Event<String>>()
    val onTransit: LiveData<Event<String>> get() = _onTransit

    private val repository = IngredientRepository.instance
    var listLiveData: MutableLiveData<IngredientResponse> = MutableLiveData()

    private var connectServerFlg : Boolean = false

    init {
        get()
    }

    fun get() = viewModelScope.launch {
        Log.d(TAG, "get() called")

        if(connectServerFlg) { return@launch }
        try {
            connectServerFlg  = true
            val id = PrefUtil(getApplication()).getPrefInt(PrefUtil.MY_FRIDGE_ID)
            val response = repository.get(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    it.ingredientList?.let { listData ->
                        ingredientList = listData
                    }
                    listLiveData.postValue(it) //データを取得したら、LiveDataを更新
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        } finally {
            connectServerFlg = false
        }
    }

    /**
     *  リストアイテムタップ時に画面遷移イベント更新.
     *  TODO タップしたリストデータを遷移時にNavigationに渡せるようにしたいがいいやり方が思いつかない.
     *  ひとまず仮でviewModel内に一時的において渡せるようにするが、気持ち悪いので直したい
     */
    fun onFixTransit(event: String, fixData: Ingredient) {
        Log.d(TAG, "onFixTransit() called with: event = $event, fixData = $fixData")
        ingredient = fixData
        onTransit(event)
    }

    /** 画面遷移イベント更新. */
    fun onTransit(event: String) {
        _onTransit.value = Event(event)
    }

    /** ジャンルごとにデータをMap. */
    fun mapData(position: Int): List<Ingredient> {
        Log.d(TAG, "mapData() called")
        val mapList = mutableListOf<Ingredient>()
        for (i in ingredientList!!.indices) {
            // positionとジャンルIDが一致した場合はリストに追加.
            // position0の場合はALLタブのため全て追加.
            if (position == 0 || ingredientList!![i].genre == position) {
                mapList.add(ingredientList!![i])
            }
        }
        return mapList
    }

    fun logOut() {
        UserLoginRepository.instance.onSignOut()
    }
}