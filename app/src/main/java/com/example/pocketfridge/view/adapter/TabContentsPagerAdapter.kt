package com.example.pocketfridge.view.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pocketfridge.model.response.IngredientData
import com.example.pocketfridge.view.fragment.ListFragment

/**
 * tab pager adapter.
 */
class TabContentsPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val dataList: ArrayList<IngredientData>
) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "TabContentsPagerAdapter"
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount() called")
        return 7
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment() called with: position = $position")
        return ListFragment(mapData(position))
    }

    /** ジャンルごとにデータをMap. */
    private fun mapData(position: Int): ArrayList<IngredientData> {
        Log.d(TAG, "mapData() called")
        val mapDataList = ArrayList<IngredientData>()
        for (i in 0 until dataList.size) {
            // positionとジャンルIDが一致した場合はリストに追加.
            // position0の場合はALLタブのため全て追加.
            if (position == 0 || dataList[i].genre == position.toString()) {
                mapDataList.add(dataList[i])
            }
        }
        return mapDataList
    }

}
