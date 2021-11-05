package com.example.pocketfridge.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketfridge.databinding.IngredientItemBinding
import com.example.pocketfridge.model.response.IngredientData

/**
 * list item adapter.
 */
class RecyclerAdapter(private val dataList: ArrayList<IngredientData>?) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "RecyclerAdapter"
    }

    /** ビューバインディング. */
    private lateinit var binding: IngredientItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called")
        binding = IngredientItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() called with: viewHolder = $viewHolder")
        dataList?.let {
            val ingredientData = it[position]
            viewHolder.bind(ingredientData)
            viewHolder.setItemMargin(position)
        }
    }

    /** アイテム数を返す. */
    override fun getItemCount(): Int = dataList?.size ?: 0


    /**
     * ビューホルダー.
     */
    class ViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /** ビューバインド. */
        fun bind(data: IngredientData) {
            Log.d(TAG, "ViewHolder bind() called with: data = $data")
            binding.ingredientName.text = data.name
            binding.useByDate.text = data.useByDate
//            binding.imageView.drawable = data.name
            binding.left.text = data.left.toString()
        }

        fun setItemMargin(position: Int) {
            val layoutParams = binding.listLayout.layoutParams as ViewGroup.MarginLayoutParams
            val marginTop = if (position != 0) 20 else 5
            layoutParams.topMargin = marginTop
        }
    }
}

