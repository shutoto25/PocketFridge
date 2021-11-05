package com.example.pocketfridge.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketfridge.databinding.ActivityAddBinding

class AddActivity  : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "MainActivity"
    }

    /** viewBinding. */
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}