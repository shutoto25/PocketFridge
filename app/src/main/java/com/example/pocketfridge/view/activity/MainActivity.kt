package com.example.pocketfridge.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pocketfridge.databinding.ActivityMainBinding

/**
 * スタート画面.
 * TODO ログイン系はFirebaseで実装予定
 */
class MainActivity : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "MainActivity"
    }

    /** viewBinding. */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, TabContentsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}