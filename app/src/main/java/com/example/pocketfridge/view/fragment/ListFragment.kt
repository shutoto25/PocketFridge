package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketfridge.databinding.FragmentListBinding
import com.example.pocketfridge.model.response.IngredientData
import com.example.pocketfridge.view.adapter.RecyclerAdapter

/**
 * 食材一覧表示Fragment.
 */
class ListFragment(private val mapDataList: ArrayList<IngredientData>) : Fragment(){

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientListFragment"
    }

    /** view model. */

    /** dataBinding. */
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    /** RecyclerAdapter. */
    private var recyclerAdapter: RecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")

        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = RecyclerAdapter(mapDataList)
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