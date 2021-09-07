package com.example.whatsinthefridge.model.response

data class LoginResponse(
    val responseCode: Int,
    val resultCode: Int,
    val loginCode: String
)