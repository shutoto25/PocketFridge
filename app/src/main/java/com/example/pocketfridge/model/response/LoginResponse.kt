package com.example.pocketfridge.model.response

data class LoginResponse(
    val responseCode: Int,
    val resultCode: Int,
    val loginCode: String
)