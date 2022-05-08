package com.example.pocketfridge.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.pocketfridge.R
import com.example.pocketfridge.model.data.GenreType
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.utility.DateUtil
import com.example.pocketfridge.utility.PrefUtil
import com.example.pocketfridge.view.callback.Event
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel 層の役割
 * ViewModel 層は次のことを受け持ちます。
 * UI の状態の保持（ラジオボタンはどれが選択されているか、テキストフィールドには何が入力されているか、など）
 * UI コンポーネントに直接依存しない表示のロジック（テキストフィールドが空でなくなったらボタンを押下可能にする、など）
 * UI コンポーネント同士の連携は View 層で行ってはいけません。それは ViewModel 層の仕事です。
 * ViewModel 層は UI コンポーネント（View クラスなど）の型などを知っていてはいけません。それは View 層の仕事です。
 */
class IngredientViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientViewModel"
    }

    /** 画面遷移イベント */
    private val _onTransit = MutableLiveData<Event<String>>()
    val onTransit: LiveData<Event<String>> get() = _onTransit

    /** 食材データ更新リポジトリ. */
    private val repository = IngredientRepository.instance

    // id(新規追加時は0)
    val id = MutableLiveData(0)
    val name = MutableLiveData<String>()
    val date = MutableLiveData(DateUtil().todayString())
    val genre = MutableLiveData(GenreType.ALL.id)
    val left = MutableLiveData(100)

    /** 食材情報更新時は一度情報を更新する. */
    fun setData(ingredient: Ingredient) {
        Log.d("IngredientViewModel", "setData() called with: ingredient = $ingredient")
        ingredient.id.let { id.value = it }
        ingredient.name.let { name.value = it }
        ingredient.useByDate?.let { date.value = it }
        ingredient.genre.let { genre.value = it }
        ingredient.left?.let { left.value = it }
    }

    /** 食材データを追加. */
    fun post(context: Context) = viewModelScope.launch {
        Log.d(TAG, "post() called")
        try {
            val response = repository.post(createRequestBody())
            Log.d(TAG, "post() called with: res = ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    onTransit(context.resources.getString(R.string.event_transit_home))
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    /** 食材データを更新. */
    fun put(context: Context) = viewModelScope.launch {
        Log.d(TAG, "put() called")
        try {
            val response = repository.put(createRequestBody())
            Log.d(TAG, "put() called with: res = ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    onTransit(context.resources.getString(R.string.event_transit_home))
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    /** 食材データを削除. */
    fun delete(context: Context) = viewModelScope.launch {
        Log.d(TAG, "delete() called")
        try {
            val response = repository.delete(id.value!!)
            Log.d(TAG, "delete() called with: res = ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    onTransit(context.resources.getString(R.string.event_transit_home))
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    /** 登録するデータが有効か. */
    fun isValid() = id.value != null && !(name.value.isNullOrEmpty())

    /** POSTデータ生成. */
    private fun createRequestBody(): Ingredient {
        return Ingredient(
            id.value!!,
            name.value!!,
            genre.value!!,
            left.value,
            date.value,
            null,
            PrefUtil(getApplication()).getPrefInt(PrefUtil.MY_FRIDGE_ID) //固定値で現在のグループIDを渡す.
        )
    }

    /** 画面遷移イベント更新. */
    private fun onTransit(event: String) {
        _onTransit.value = Event(event)
    }
}