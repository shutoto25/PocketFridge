package com.example.pocketfridge.view.fragment

import android.content.Intent
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
import com.example.pocketfridge.utility.PrefUtil
import com.example.pocketfridge.view.activity.UserLoginActivity
import com.example.pocketfridge.view.adapter.TabContentsPagerAdapter
import com.example.pocketfridge.view.callback.EventObserver
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
    private val listViewModel by lazy {
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
        binding.apply {
            isLoading = true
            hasData = true
            viewModel = listViewModel
        }
        // リスナー登録.
        setListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        // 画面遷移.
        listViewModel.onTransit.observe(viewLifecycleOwner, EventObserver {
            val action = TabFragmentDirections.actionTabToDetail(listViewModel.ingredient)
            findNavController().navigate(action)
            listViewModel.ingredient = null
        })
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
        listViewModel.listLiveData.observe(viewLifecycleOwner) {
            Log.d(TAG, "listLiveData observer.")
            binding.apply {
                isLoading = false
                // viewPager初期化.
                viewPager.apply {
                    listViewModel.ingredientList?.let {
                        adapter = TabContentsPagerAdapter(this@TabFragment, listViewModel)  // アダプタ.
                        orientation = ViewPager2.ORIENTATION_HORIZONTAL  // スワイプ向き.
                        offscreenPageLimit = 3  // 保存画面数.
                    } ?: run {
                        hasData = false
                    }
                    // viewPagerとtabLayoutを紐付け.
                    TabLayoutMediator(tabLayout, this) { tab, position ->
                        tab.text = linkTabToTitle(position)
                    }.attach()
                }
            }
        }
    }

    /* -------------- UI ------------------ */
    private fun linkTabToTitle(position: Int) = when (position) {
        0 -> "all"
        1 -> "meat"
        2 -> "vegetable"
        3 -> "fish"
        4 -> "Refrigerate"
        5 -> "frozen"
        6 -> "others"
        else -> "" // ありえない
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
        Log.d(TAG, "onNavigationItemSelected() called with: item = ${item.itemId}")
        when (item.itemId) {
            R.id.menu_exit_fridge -> {
                val pref = PrefUtil(requireContext())
                pref.removePref(PrefUtil.MY_FRIDGE_ID)
                pref.removePref(PrefUtil.MY_FRIDGE_NAME)
                pref.removePref(PrefUtil.MY_FRIDGE_PASSWORD)
                findNavController().navigate(R.id.action_tab_to_fridgeLogin)
            }
            R.id.menu_logout -> {
                listViewModel.logOut()
                val intent = Intent(context, UserLoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        return false
    }

}