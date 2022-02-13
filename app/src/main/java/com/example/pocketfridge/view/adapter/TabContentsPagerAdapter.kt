package com.example.pocketfridge.view.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.view.fragment.ListFragment

/**
 * tab pager adapter.
 */
class TabContentsPagerAdapter(
    fragment: Fragment, private val ingredientList: List<Ingredient>
    ) : FragmentStateAdapter(fragment) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "TabContentsPagerAdapter"
    }

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment() called with: position = $position")
        return ListFragment(mapData(position))
    }

    /** ジャンルごとにデータをMap. */
    private fun mapData(position: Int): List<Ingredient> {
        Log.d(TAG, "mapData() called")
        val mapList = mutableListOf<Ingredient>()
        for (i in ingredientList.indices) {
            // positionとジャンルIDが一致した場合はリストに追加.
            // position0の場合はALLタブのため全て追加.
            if (position == 0 || ingredientList[i].genre == position) {
                mapList.add(ingredientList[i])
            }
        }
        return mapList
    }
}
