package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.response.IngredientResponse
import com.example.pocketfridge.model.response.LoginResponse
import retrofit2.http.GET
import rx.Observable

interface ApiClient {

    @GET("api/login")
    fun getLogin(): Observable<LoginResponse>

    @GET("ingredient")
    fun ingredientInfo(): Observable<IngredientResponse>


}