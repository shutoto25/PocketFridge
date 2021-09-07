package com.example.whatsinthefridge.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatsinthefridge.R
import com.example.whatsinthefridge.databinding.ActivityMainBinding
import com.example.whatsinthefridge.model.repsitory.IngredientRepository
import com.example.whatsinthefridge.model.response.IngredientResponse
import rx.Observer

/**
 * スタート画面.
 */
class MainActivity : AppCompatActivity() {

    /** viewBinding. */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        IngredientRepository().fetch(createObserver())
    }


    /** observer作成. */
    private fun createObserver() = object : Observer<IngredientResponse> {
        override fun onNext(t: IngredientResponse?) {
            TODO("Not yet implemented")
        }

        override fun onError(e: Throwable?) {
            TODO("Not yet implemented")
        }

        override fun onCompleted() {
            TODO("Not yet implemented")
        }
    }


}