package com.example.pocketfridge.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketfridge.model.data.GenreType
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.view.callback.NavigationBack
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
class IngredientViewModel : ViewModel() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientViewModel"
    }

    private val repository = IngredientRepository.instance

    // id(新規追加時は0)
    val id = MutableLiveData(0)
    val name = MutableLiveData<String>()
    val date = MutableLiveData(Date())
    val genre = MutableLiveData(GenreType.ALL.id)
    val left = MutableLiveData(100)

    fun setData(ingredient: Ingredient) {
        Log.d("IngredientViewModel", "setData() called with: ingredient = $ingredient")
        ingredient.id.let { id.value = it }
        ingredient.name.let { name.value = it }
        ingredient.useByDate?.let { date.value = it }
        ingredient.genre.let { genre.value = it }
        ingredient.left?.let { left.value = it }
    }

    fun post(navigationBack: NavigationBack) = viewModelScope.launch {
        Log.d(TAG, "post() called")
        try {
            if (isValid()) {
                val response = repository.post(createRequestBody())
                Log.d(TAG, "post() called with: res = ${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        navigationBack.onBack(res.resultCode == 0)
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun put(navigationBack: NavigationBack) = viewModelScope.launch {
        Log.d(TAG, "put() called")
        try {
            if (isValid()) {
                val response = repository.put(createRequestBody())
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        Log.d(TAG, "put() called with: res = ${res.resultCode}")
                        navigationBack.onBack(res.resultCode == 0)
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun delete(navigationBack: NavigationBack) = viewModelScope.launch {
        Log.d(TAG, "delete() called")
        try {
            if (isValid()) {
                val response = repository.delete(id.value!!)
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        Log.d(TAG, "delete() called with: res = ${res.resultCode}")
                        navigationBack.onBack(res.resultCode == 0)
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    /** データが有効か. */
    private fun isValid() = id.value != null || !(name.value.isNullOrEmpty())

    /** POSTデータ生成. */
    private fun createRequestBody(): Ingredient {
        return Ingredient(
            id.value!!, name.value!!, genre.value!!, left.value, date.value, null,
        )
    }
}