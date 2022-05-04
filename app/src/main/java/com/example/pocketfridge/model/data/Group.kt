package com.example.pocketfridge.model.data

import java.io.Serializable

/**
 * グループ情報.
 */
data class Group(
    /** グループ名. */
    val name: String,
    /** パスワード. */
    val password: String
) : Serializable
