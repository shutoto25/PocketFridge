package com.example.pocketfridge.view.callback

import com.example.pocketfridge.model.data.Ingredient

interface CardClickCallback {
    fun onCardClick(ingredient: Ingredient)
}

interface FabClickCallback {
    fun onFabClick()
}