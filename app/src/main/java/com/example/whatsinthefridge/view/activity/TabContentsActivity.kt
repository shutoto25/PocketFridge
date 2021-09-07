package com.example.whatsinthefridge.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsinthefridge.databinding.ActivityTabContentsBinding

/**
 * タブコンテンツ画面.
 */
class TabContentsActivity : AppCompatActivity() {

    /** viewBinding. */
    private lateinit var binding: ActivityTabContentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTabContentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}