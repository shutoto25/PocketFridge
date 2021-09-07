package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.response.IngredientResponse
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class IngredientRepository {

    fun fetch(observer: Observer<IngredientResponse>) {

        // 適当なものに変えてとりあえず接続ができるかかくにんする　天気とか

        ApiClientManager()
            .getApiClient("https://192.168.0.16/")
            .getAllIngredient()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(observer)
    }
}