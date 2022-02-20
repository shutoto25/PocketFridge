package com.example.pocketfridge.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.IngredientItemBinding
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.viewModel.ListViewModel

/**
 * list item adapter.
 */
class IngredientListAdapter(private val viewModel: ListViewModel) :
    RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "RecyclerAdapter"
    }

    /** ジャンルタブに該当するデータリスト. */
    private var mapDataList: List<Ingredient>? = null

    fun setIngredientList(tabPosition: Int) {
        Log.d(TAG, "setIngredientList() called")
            mapDataList = viewModel.mapData(tabPosition)
            notifyItemRangeInserted(0, mapDataList!!.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        Log.d(TAG, "onCreateViewHolder() called")
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.ingredient_item, parent, false
        ) as IngredientItemBinding
        binding.apply {
           viewModel  = this@IngredientListAdapter.viewModel
        }
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: IngredientViewHolder, listPosition: Int) {
        Log.d(TAG, "onBindViewHolder() called with: viewHolder = $viewHolder")
        viewHolder.binding.ingredient = mapDataList?.get(listPosition)
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = mapDataList?.size ?: 0


    /**
     * ビューホルダー.
     */
    open class IngredientViewHolder(val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}

