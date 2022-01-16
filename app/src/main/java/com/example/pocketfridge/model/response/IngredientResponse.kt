package com.example.pocketfridge.model.response

import com.example.pocketfridge.model.data.Ingredient
import java.io.Serializable

/**
 * 食材一覧レスポンス.
 */
data class IngredientResponse(
    /** 取得結果コード. */
    val resultCode: Int?,
    /** 食材情報一覧. */
    val ingredientList: List<Ingredient>?
//        /** ユーザー情報. */
//        val user: User?
) : Serializable