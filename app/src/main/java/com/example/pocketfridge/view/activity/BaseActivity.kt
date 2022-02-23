package com.example.pocketfridge.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pocketfridge.R

/**
 * Fragmentベース画面.
 */
class BaseActivity : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "BaseActivity"
    }

    /* -------------- life cycle ------------------ */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        setContentView(R.layout.activity_base)
    }
}