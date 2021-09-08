package com.example.pocketfridge.model.response

/**
 * ログイン情報レスポンス.
 */
data class LoginResponse(
    /** Httpレスポンスコード. */
    val httpResponseCode: Int,
    /** 取得結果コード. */
    val resultCode: Int,
    /** ログインId. */
    val loginId: String
)