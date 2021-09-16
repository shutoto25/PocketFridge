package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketfridge.databinding.FragmentIngredientListBinding
import com.example.pocketfridge.view.adapter.RecyclerAdapter
import kotlin.math.log

/**
 * 食材一覧表示Fragment.
 */
class IngredientListFragment : Fragment() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientListFragment"
    }

    /** viewBinding. */
    private var _binding: FragmentIngredientListBinding? = null
    private val binding get() = _binding!!

    private var recyclerAdapter: RecyclerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        _binding = FragmentIngredientListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        val ms = mutableListOf("a", "b", "c")
        recyclerAdapter = RecyclerAdapter(ms)
        binding.recyclerView.adapter = recyclerAdapter
        recyclerAdapter!!.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView() called")

        recyclerAdapter?.let {
            recyclerAdapter = null
            binding.recyclerView.adapter = null
        }
        // メモリリーク対策のため、本契機でbindingを破棄する.
        _binding = null
    }

}