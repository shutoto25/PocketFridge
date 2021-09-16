package com.example.pocketfridge.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketfridge.databinding.IngredientItemBinding

/**
 * list item adapter.
 */
class RecyclerAdapter(private val dataList: MutableList<String>?) :
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
            val data = dataList[position]
            viewHolder.bind(data)
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
        fun bind(text: String) {
            Log.d(TAG, "ViewHolder bind() called with: text = $text")
            binding.listItemText.text = text
        }
    }
}

