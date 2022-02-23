package com.example.pocketfridge.view.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pocketfridge.view.fragment.ListFragment
import com.example.pocketfridge.viewModel.ListViewModel

/**
 * tab pager adapter.
 */
class TabContentsPagerAdapter(
    fragment: Fragment, private val viewModel: ListViewModel
) : FragmentStateAdapter(fragment) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "TabContentsPagerAdapter"
    }

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment() called with: position = $position")
        return ListFragment(position, viewModel)
    }
}
