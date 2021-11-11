package com.example.pocketfridge.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.ActivityTabContentsBinding
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.model.response.IngredientData
import com.example.pocketfridge.model.response.IngredientResponse
import com.example.pocketfridge.view.adapter.TabContentsPagerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import rx.Observer


/**
 * タブコンテンツ画面.
 */
class TabContentsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "TabContentsActivity"
    }

    /** pageAdapter. */
    private lateinit var viewPagerAdapter: TabContentsPagerAdapter

    /** viewBinding. */
    private lateinit var binding: ActivityTabContentsBinding


    /* -------------- life cycle ------------------ */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityTabContentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // リスナー登録.
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")

        setProgressCircular(true)
        IngredientRepository().getAll(createObserver())
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    /* -------------- UI ------------------ */
    /** 食材一覧リスト更新. */
    private fun updateList(dataList: ArrayList<IngredientData>) {
        Log.d(TAG, "updateList() called")

        viewPagerAdapter = TabContentsPagerAdapter(this, dataList)

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
            tab.text = when (position) {
                0 -> "all"
                1 -> "meat"
                2 -> "vegetable"
                3 -> "fish"
                4 -> "Refrigerate"
                5 -> "frozen"
                6 -> "others"
                else -> "" // ありえない
            }
        }.attach()
    }

    private fun setVisibility(dataList: ArrayList<IngredientData>?) {
        Log.d(TAG, "setVisibility() called")

        if (dataList.isNullOrEmpty()) {
            binding.noData.visibility = View.VISIBLE
            binding.viewPager.visibility = View.GONE
        } else {
            binding.noData.visibility = View.GONE
            binding.viewPager.visibility = View.VISIBLE
        }
    }

    /** プログレスサークス設定. */
    private fun setProgressCircular(isStart :Boolean) {
        Log.d(TAG, "setProgressCircular() called with: isStart = $isStart")
        binding.progressCircular.visibility = if (isStart) View.VISIBLE else View.GONE
    }

    /** リスナー設定. */
    private fun setListeners() {
        Log.d(TAG, "setListeners() called")
        // ドロワーリスナー設定.
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.topAppBar,
            R.string.nav_open,
            R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)

        // fabのリスナ設定.
        binding.fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        // pull to refresh.
        binding.swipeRefresh.setOnRefreshListener {
            IngredientRepository().getAll(createObserver())
        }
    }

    /** ドロワーメニューリスナー設定. */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return false
    }


    /* -------------- server connection ------------------ */
    /** observer作成. */
    private fun createObserver() = object : Observer<IngredientResponse> {
        override fun onNext(response: IngredientResponse) {
            Log.d(TAG, "Observer.onNext() called with: response = $response")
            if (response.resultCode == 0) {
                response.IngredientList?.let { updateList(it) }
            }
        }

        override fun onError(error: Throwable?) {
            Log.d(TAG, "Observer.onError() called with: error = $error")

            setProgressCircular(false)

            // pull to refreshのローディングをとめる.
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        override fun onCompleted() {
            Log.d(TAG, "Observer.onCompleted() called")

            setProgressCircular(false)

            // pull to refreshのローディングをとめる.
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }
}