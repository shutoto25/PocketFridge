package com.example.pocketfridge.model.data

import java.io.Serializable

/**
 * 更新情報.
 */
data class User(
    /** ユーザー名. */
    val userName: String,
    /** 更新日時. */
    val Date: Long
) : Serializable