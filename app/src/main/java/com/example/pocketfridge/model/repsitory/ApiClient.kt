package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.response.IngredientResponse
import com.example.pocketfridge.model.response.LoginResponse
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable

interface ApiClient {

    @GET("api/login")
    fun getLogin(): Observable<LoginResponse>

    @GET("api/ingredient/get")
    fun getAllIngredient(): Observable<IngredientResponse>

    @POST("api/ingredient/set")
    fun setAllIngredient(): Observable<Int>


}