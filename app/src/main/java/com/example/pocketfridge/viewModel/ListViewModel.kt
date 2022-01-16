package com.example.pocketfridge.viewModel

import androidx.lifecycle.*
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.model.response.IngredientResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ListFragmentに紐づけて、リストが更新したときにアプリ内に保持しているリストも？更新する
 * 更新したときにListFragment側のObserverが反応して更新が走るてきな？LiveData
 */
class ListViewModel : ViewModel() {

    private val repository = IngredientRepository.instance
    var listLiveData: MutableLiveData<IngredientResponse> = MutableLiveData()

    init {
        get()
    }

    private fun get() = viewModelScope.launch {
        try {
            val response = repository.get()
            if (response.isSuccessful) {
                response.body()?.let {
                    listLiveData.postValue(it) //データを取得したら、LiveDataを更新
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}