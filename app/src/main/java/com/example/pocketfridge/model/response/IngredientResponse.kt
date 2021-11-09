package com.example.pocketfridge.model.response

/**
 * 食材一覧レスポンス.
 */
data class IngredientResponse(
        /** 取得結果コード. */
        val resultCode: Int?,
        /** 食材情報一覧. */
        val IngredientList: ArrayList<IngredientData>?
)

/**
 * 食材情報.
 */
data class IngredientData(
        /** 食材名. */
        val name: String,
        /** ジャンル. */
        val genre: String,
        /** 期限. */
        val useByDate: Long?,
        /** 食材画像. */
        val image: String?,
        /** 残量パーセント(left/100). */
        val left: Int?
//        /** 更新情報. */
//        val updateData: UpdateData?
)

/**
 * 更新情報.
 */
data class UpdateData(
        /** ユーザー名. */
        val userName: String,
        /** 更新日時. */
        val Date: Long
)