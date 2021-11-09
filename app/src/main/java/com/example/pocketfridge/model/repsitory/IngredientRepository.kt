package com.example.pocketfridge.model.repsitory

import android.util.Log
import com.example.pocketfridge.model.response.IngredientData
import com.example.pocketfridge.model.response.IngredientResponse
import okhttp3.RequestBody
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.math.E

/**
 * サーバデータベース操作リポジトリ.
 */
class IngredientRepository {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientRepository"

        /** end point. */
        private const val END_POINT = "https://server-side-fridge-api.herokuapp.com/"
    }

    /** 全データ取得. */
    fun getAll(observer: Observer<IngredientResponse>) {
        Log.d(TAG, "getAll() called")
        ApiClientManager()
                .getApiClient(END_POINT)
                .getAllIngredient()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer)
    }

    /** 新規追加. */
    fun post(observer: Observer<IngredientResponse>, body: IngredientData) {
        Log.d(TAG, "post() called")
        ApiClientManager()
                .getApiClient(END_POINT)
                .createIngredient(body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer)
    }

    /** インデックス[id]のデータ更新. */
    fun put(observer: Observer<IngredientResponse>, id: Int, body: IngredientData) {
        Log.d(TAG, "put() called")
        ApiClientManager()
                .getApiClient(END_POINT)
                .updateIngredient(id, body)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer)
    }

    /** インデックス[id]のデータ削除. */
    fun delete(observer: Observer<IngredientResponse>, id: Int) {
        Log.d(TAG, "delete() called")
        ApiClientManager()
                .getApiClient(END_POINT)
                .deleteIngredient(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer)
    }
}