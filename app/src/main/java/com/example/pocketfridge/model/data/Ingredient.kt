package com.example.pocketfridge.model.data

import java.io.Serializable
import java.util.*

/**
 * 食材情報.
 */
data class Ingredient(
    /** id. */
    val id: Int,
    /** 食材名. */
    val name: String,
    /** ジャンル. */
    val genre: Int,
    /** 残量パーセント(left/100). */
    val left: Int?,
    /** 期限日(yyyy/MM/dd). */
    val useByDate: String?,
    /** 食材画像. */
    val image: String?,
    /** グループID */
    val groupId: Int,
) : Serializable
