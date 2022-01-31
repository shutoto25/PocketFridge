package com.example.pocketfridge.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.repsitory.IngredientRepository
import kotlinx.coroutines.launch
import java.util.*

// viewのあたいとここの値が常に紐付いている状態にしたい
// そしてそれをPOSTにわたしたい

/**
 * ViewModel 層の役割
 * ViewModel 層は次のことを受け持ちます。
 * UI の状態の保持（ラジオボタンはどれが選択されているか、テキストフィールドには何が入力されているか、など）
 * UI コンポーネントに直接依存しない表示のロジック（テキストフィールドが空でなくなったらボタンを押下可能にする、など）
 * UI コンポーネント同士の連携は View 層で行ってはいけません。それは ViewModel 層の仕事です。
 * ViewModel 層は UI コンポーネント（View クラスなど）の型などを知っていてはいけません。それは View 層の仕事です。
 */
class IngredientViewModel : ViewModel() {

    private val repository = IngredientRepository.instance

    // id(新規追加時は0)
    val id = MutableLiveData<Int>()
    val _id: LiveData<Int> get() = id

    val name = MutableLiveData<String>()
    val _name: LiveData<String> get() = name

    val date = MutableLiveData(Date())

    val genre = MutableLiveData("")

    val left = MutableLiveData<Int>()
    val _left: LiveData<Int> get() = left


    fun setData(ingredient: Ingredient?) {
        Log.d("IngredientViewModel", "setData() called with: ingredient = $ingredient")
        ingredient?.id?.let { id.postValue(it) }
        ingredient?.name?.let { name.postValue(it) }
        ingredient?.useByDate?.let { date.value = it }
        ingredient?.genre?.let { genre.value = it }
        ingredient?.left?.let { left.postValue(it) }
    }

    fun post() = viewModelScope.launch {
        Log.d("IngredientViewModel", "post() called")
        try {
            createTestRequestBody()?.let {
                val response = repository.post(it)
                if (response.isSuccessful) {
                    response.body()?.let {
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun put() = viewModelScope.launch {
        Log.d("IngredientViewModel", "put() called")
        try {
            createTestRequestBody()?.let {
                val response = repository.put(75, it)
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        Log.d("IngredientViewModel", "put() called with: res = ${res.resultCode}")
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun delete() = viewModelScope.launch {
        Log.d("IngredientViewModel", "delete() called")
        try {
            _id.value?.let {
                val response = repository.delete(it)
                if (response.isSuccessful) {
                    response.body()?.let { res ->
                        Log.d(
                            "IngredientViewModel",
                            "delete() called with: res = ${res.resultCode}"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    /** POSTデータ生成. */
    private fun createRequestBody(): Ingredient {
        return Ingredient(
            id.value!!,
            name.value!!,
            genre.value!!,
            date.value,
            null,
            left.value
        )
    }

    private fun createTestRequestBody(): Ingredient {
        return Ingredient(
            0,
            "テスト",
            "1",
            Date(),
            null,
            100
        )
    }
}