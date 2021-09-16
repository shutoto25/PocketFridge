package com.example.pocketfridge.view.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pocketfridge.view.fragment.IngredientListFragment

/**
 * tab pager adapter.
 */
class TabContentsPagerAdapter(fragmentActivity: FragmentActivity) :
FragmentStateAdapter(fragmentActivity) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "TabContentsPagerAdapter"
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount() called")
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment() called with: position = $position")
        return IngredientListFragment()
    }

}
