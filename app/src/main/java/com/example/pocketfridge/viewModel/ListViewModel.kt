package com.example.pocketfridge.viewModel

import androidx.lifecycle.ViewModel
import com.example.pocketfridge.model.response.IngredientData

class ListViewModel : ViewModel() {

    private val ingredientList = mutableListOf<IngredientData>()


    fun onClickItem(data: IngredientData) {
    }

}