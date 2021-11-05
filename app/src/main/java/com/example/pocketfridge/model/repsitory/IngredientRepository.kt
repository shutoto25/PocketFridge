package com.example.pocketfridge.model.repsitory

import com.example.pocketfridge.model.response.IngredientResponse
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class IngredientRepository {

    fun fetch(observer: Observer<IngredientResponse>) {

        ApiClientManager()
            .getApiClient("https://server-side-fridge-api.herokuapp.com/")
            .ingredientInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(observer)
    }
}