package com.example.pocketfridge.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pocketfridge.view.fragment.IngredientListFragment

/**
 * adapter.
 */
class TabContentsPagerAdapter(fragmentActivity: FragmentActivity) :
FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {

        return IngredientListFragment()

    }


}
