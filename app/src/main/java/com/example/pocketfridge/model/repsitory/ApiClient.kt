package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.data.Group
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.response.GroupResponse
import com.example.pocketfridge.model.response.IngredientResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * REST APIでの設計を目指す
 */
interface ApiClient {

    @GET("ingredient/{groupId}")
    suspend fun getAllIngredient(@Path("groupId") groupId: Int): Response<IngredientResponse>

    @POST("ingredient")
    suspend fun createIngredient(@Body body: Ingredient): Response<IngredientResponse>

    @PUT("ingredient")
    suspend fun updateIngredient(@Body body: Ingredient): Response<IngredientResponse>

    @DELETE("ingredient/{id}")
    suspend fun deleteIngredient(@Path("id") id: Int): Response<IngredientResponse>

    /* --------------- グループテープルリクエスト. ----------------- */
    @POST("group")
    suspend fun signGroup(@Body body: Group): Response<GroupResponse>

    @DELETE("group/{id}")
    suspend fun deleteGroup(@Path("groupId") groupId: String): Response<GroupResponse>
}