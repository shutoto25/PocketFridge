package com.example.pocketfridge.view.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.pocketfridge.databinding.ActivityTabContentsBinding
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.model.response.IngredientResponse
import com.example.pocketfridge.view.adapter.TabContentsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import rx.Observer

/**
 * タブコンテンツ画面.
 */
class TabContentsActivity : AppCompatActivity() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "TabContentsActivity"
    }

    /** pageAdapter. */
    private val viewPagerAdapter by lazy { TabContentsPagerAdapter(this) }

    /** viewBinding. */
    private lateinit var binding: ActivityTabContentsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityTabContentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        IngredientRepository().fetch(createObserver())

        // viewPager初期化.
        binding.viewPager.apply {
            // アダプタ.
            adapter = viewPagerAdapter
            // スワイプ向き.
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            // 保存画面数.
            offscreenPageLimit = 3
        }

        // viewPagerとtabLayoutを紐付け.
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = position.toString()
        }.attach()

        // fabのリスナ設定.
        binding.fab.setOnClickListener {
            // 追加画面起動
        }

    }


    /** observer作成. */
    private fun createObserver() = object : Observer<IngredientResponse> {
        override fun onNext(t: IngredientResponse?) {
        }

        override fun onError(e: Throwable?) {
        }

        override fun onCompleted() {
        }
    }
}