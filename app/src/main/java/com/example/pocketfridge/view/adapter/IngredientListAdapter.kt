package com.example.pocketfridge.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.IngredientItemBinding
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.view.callback.CardClickCallback


/**
 * list item adapter.
 */
class IngredientListAdapter(private val clickCallback: CardClickCallback?) :
    RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder>(){

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "RecyclerAdapter"
    }

    private var ingredientList: List<Ingredient>? = null

    fun setIngredientList(ingredientList: List<Ingredient>) {
        Log.d(TAG, "setIngredientList() called")
        if (this.ingredientList == null) {
            this.ingredientList = ingredientList
            notifyItemRangeInserted(0, ingredientList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    Log.d(TAG, "getOldListSize() called")
                    return requireNotNull(this@IngredientListAdapter.ingredientList).size
                }

                override fun getNewListSize(): Int {
                    Log.d(TAG, "getNewListSize() called")
                    return ingredientList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    Log.d(TAG, "areItemsTheSame() called")
                    val oldList = this@IngredientListAdapter.ingredientList
                    return oldList?.get(oldItemPosition)?.id == ingredientList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    Log.d(TAG, "areContentsTheSame() called")
                    val project = ingredientList[newItemPosition]
                    val old = ingredientList[oldItemPosition]
                    return project.id == old.id
                }
            })
            this.ingredientList = ingredientList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        Log.d(TAG, "onCreateViewHolder() called")
       val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
           R.layout.ingredient_item, parent, false) as IngredientItemBinding
        binding.apply {
            callback = clickCallback
        }
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: IngredientViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() called with: viewHolder = $viewHolder")
        viewHolder.binding.ingredient = ingredientList?.get(position)
        viewHolder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = ingredientList?.size ?: 0


    /**
     * ビューホルダー.
     */
    open class IngredientViewHolder(val binding: IngredientItemBinding)
        : RecyclerView.ViewHolder(binding.root)
}

