package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.response.IngredientData
import com.example.pocketfridge.model.response.IngredientResponse
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable

/**
 * REST APIでの設計を目指す
 */
interface ApiClient {

    @GET("ingredient")
    fun getAllIngredient(): Observable<IngredientResponse>

    @POST("ingredient")
    fun createIngredient(@Body body: IngredientData): Observable<IngredientResponse>

    @PUT("ingredient/{id}")
    fun updateIngredient(@Path("id") id: Int, @Body body: IngredientData): Observable<IngredientResponse>

    @DELETE("ingredient/{id}")
    fun deleteIngredient(@Path("id") id: Int): Observable<IngredientResponse>


}