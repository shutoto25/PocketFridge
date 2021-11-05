package com.example.pocketfridge.model.response

/**
 * 食材一覧レスポンス.
 */
data class IngredientResponse(
    /** 取得結果コード. */
    val resultCode: Int?,
    /** 食材一覧. */
    val IngredientList: ArrayList<IngredientData>?,
)

/**
 * 食材データ.
 */
data class IngredientData(
    /** 食材名. */
    val name: String,
    /** ジャンル. */
    val genre: String,
    /** 期限. */
    val useByDate: String?,
    /** 食材写真. */
    val image: String?,
    /** 残量パーセント(left/100). */
    val left: Int?,
)