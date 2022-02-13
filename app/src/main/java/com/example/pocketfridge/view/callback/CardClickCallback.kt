package com.example.pocketfridge.view.callback

import com.example.pocketfridge.model.data.Ingredient

/** リストアイテムクリック. */
interface CardClickCallback {
    fun onCardClick(ingredient: Ingredient)
}

/** 食材追加FAB */
interface AddClickCallback {
    fun onAddClick()
}

/** 食材登録画面クリック */
interface IngredientDetailCallback {
    /** 日付入力. */
    fun onExpiredBoxClick()

    /** 食材登録FAB. */
    fun onRegisterClick()

    /** 食材修正FAB. */
    fun onFixClick()

    /** 食材削除FAB. */
    fun onDeleteClick()
}

interface NavigationBack {
    fun onBack(result :Boolean)
}