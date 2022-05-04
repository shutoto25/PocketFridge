package com.example.pocketfridge.model.response

import java.io.Serializable

/**
 * グループ情報レスポンス.
 */
data class GroupResponse(
    /** 取得結果コード. */
    val resultCode: Int,
    /** グループID. */
    val groupId: Int
) : Serializable