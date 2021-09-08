package com.example.pocketfridge.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pocketfridge.databinding.IngredientItemBinding

/**
 *
 */
class RecyclerAdapter(
    private val dataList: ArrayList<String>?
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    /** ビューバインディング. */
    private lateinit var binding: IngredientItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = IngredientItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind()
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

    /**
     * ビューホルダー.
     */
    class ViewHolder(binding: IngredientItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        /** ビューバインド. */
        fun bind() {
        }
    }
}

