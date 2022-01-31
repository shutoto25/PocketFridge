package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.response.IngredientResponse
import retrofit2.Response

/**
 * Model 層の役割
 * Model 層はビジネスロジックとそれに関するデータ管理を受け持ちます。
 * UI 仕様には依存しないため、
 * コマンドライン版や別のプラットフォームでも（言語やバイナリの互換性があれば）使い回せます。
 *
 * サーバデータベース操作リポジトリ.
 * ViewModelに対するデータプロバイダ.
 */
class IngredientRepository {

    /** */
    companion object Factory {
        val instance: IngredientRepository
            @Synchronized get() {
                return IngredientRepository()
            }

        /** end point. */
        private const val END_POINT = "https://server-side-fridge-api.herokuapp.com/"
    }

    /** データ取得. */
    suspend fun get(): Response<IngredientResponse> =
        ApiClientManager().getApiClient(END_POINT).getAllIngredient()

    /** 新規追加. */
    suspend fun post(body: Ingredient): Response<IngredientResponse> =
        ApiClientManager().getApiClient(END_POINT).createIngredient(body)

    /** インデックス[id]のデータ更新. */
    suspend fun put(id: Int, body: Ingredient): Response<IngredientResponse> =
        ApiClientManager().getApiClient(END_POINT).updateIngredient(id, body)

    /** インデックス[id]のデータ削除. */
    suspend fun delete(id: Int): Response<IngredientResponse> =
        ApiClientManager().getApiClient(END_POINT).deleteIngredient(id)
}