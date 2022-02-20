package com.example.pocketfridge.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.ActivityMainBinding
import com.example.pocketfridge.view.callback.EventObserver
import com.example.pocketfridge.viewModel.LoginViewModel

/**
 * スタート画面.
 */
class MainActivity : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "MainActivity"
    }

    /** ViewModel. */
    private val viewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    /** Binding. */
    private lateinit var binding: ActivityMainBinding

    /* -------------- life cycle ------------------ */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }

        viewModel.onLogin.observe(this, EventObserver {
            val intent = Intent(this, BaseActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}