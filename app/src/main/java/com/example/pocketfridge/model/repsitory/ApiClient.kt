package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.response.IngredientResponse
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

/**
 * REST APIでの設計を目指す
 */
interface ApiClient {

    @GET("ingredient")
    suspend fun getAllIngredient(): Response<IngredientResponse>

    @POST("ingredient")
    suspend fun createIngredient(@Body body: Ingredient): Response<IngredientResponse>

    @PUT("ingredient/{id}")
    suspend fun updateIngredient(@Path("id") id: Int, @Body body: Ingredient): Response<IngredientResponse>

    @DELETE("ingredient/{id}")
    suspend fun deleteIngredient(@Path("id") id: Int): Response<IngredientResponse>


}