package com.example.pocketfridge.model.repsitory

import android.util.Log
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.response.IngredientResponse
import retrofit2.Response
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
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
    suspend fun get() : Response<IngredientResponse> =
        ApiClientManager().getApiClient(END_POINT).getAllIngredient()

//    /** 新規追加. */
//    fun post(observer: Observer<IngredientResponse>, body: Ingredient) {
//        Log.d(TAG, "post() called")
//        ApiClientManager()
//                .getApiClient(END_POINT)
//                .createIngredient(body)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(observer)
//    }
//
//    /** インデックス[id]のデータ更新. */
//    fun put(observer: Observer<IngredientResponse>, id: Int, body: Ingredient) {
//        Log.d(TAG, "put() called")
//        ApiClientManager()
//                .getApiClient(END_POINT)
//                .updateIngredient(id, body)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(observer)
//    }
//
//    /** インデックス[id]のデータ削除. */
//    fun delete(observer: Observer<IngredientResponse>, id: Int) {
//        Log.d(TAG, "delete() called")
//        ApiClientManager()
//                .getApiClient(END_POINT)
//                .deleteIngredient(id)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .subscribe(observer)
//    }
}