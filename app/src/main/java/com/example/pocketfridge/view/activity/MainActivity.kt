package com.example.pocketfridge.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pocketfridge.databinding.ActivityMainBinding

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

        binding.button.setOnClickListener {
            val intent = Intent(this, TabContentsActivity::class.java)
            startActivity(intent)
        }
    }
}