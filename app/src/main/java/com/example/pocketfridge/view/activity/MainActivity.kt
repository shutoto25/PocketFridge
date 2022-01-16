package com.example.pocketfridge.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pocketfridge.R
import com.google.android.material.button.MaterialButton

/**
 * スタート画面.
 * TODO ログイン系はFirebaseで実装予定
 */
class MainActivity : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "MainActivity"
    }

    /* -------------- life cycle ------------------ */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        setContentView(R.layout.activity_main)

        val button = findViewById<MaterialButton>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}