package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentTabBinding
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.view.adapter.TabContentsPagerAdapter
import com.example.pocketfridge.view.callback.CardClickCallback
import com.example.pocketfridge.view.callback.FabClickCallback
import com.example.pocketfridge.viewModel.ListViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

/**
 * タブFragment.
 */
class TabFragment : Fragment(),
    NavigationView.OnNavigationItemSelectedListener {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "TabFragment"
    }

    /** ViewModel. */
    private val viewModel by lazy {
        ViewModelProvider(this)[ListViewModel::class.java]
    }

    /** dataBinding. */
    private lateinit var binding: FragmentTabBinding

    /* -------------- life cycle ------------------ */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tab, container, false)
        // リスナー登録.
        setListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        binding.apply {
            callback = object : FabClickCallback {
                override fun onFabClick() {
                    findNavController().navigate(R.id.action_tab_to_detail)
                }
            }
        }

        viewModel.listLiveData.observe(viewLifecycleOwner) { ingredients ->
            Log.d(TAG, "listLiveData observer.")
            ingredients?.ingredientList?.let {
                // viewPager初期化.
                binding.viewPager.apply {
                    // アダプタ.
                    adapter = TabContentsPagerAdapter(this@TabFragment, it)
                    // スワイプ向き.
                    orientation = ViewPager2.ORIENTATION_HORIZONTAL
                    // 保存画面数.
                    offscreenPageLimit = 3
                }
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
    }

    /* -------------- UI ------------------ */
    /** プログレスサークス設定. */
    private fun setProgressCircular(isStart: Boolean) {
        Log.d(TAG, "setProgressCircular() called with: isStart = $isStart")
        binding.progressCircular.visibility = if (isStart) View.VISIBLE else View.GONE
    }

    /** リスナー設定. */
    private fun setListeners() {
        Log.d(TAG, "setListeners() called")
        // ドロワーリスナー設定.
        val toggle = ActionBarDrawerToggle(
            activity,
            binding.drawerLayout,
            binding.topAppBar,
            R.string.nav_open,
            R.string.nav_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationView.setNavigationItemSelectedListener(this)

    }

    /** ドロワーメニューリスナー設定. */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return false
    }

}