package com.example.pocketfridge.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketfridge.databinding.ActivityBaseBinding

/**
 * Fragmentベース画面.
 */
class BaseActivity : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "BaseActivity"
    }

    /** viewBinding. */
    private lateinit var binding: ActivityBaseBinding

    /* -------------- life cycle ------------------ */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}