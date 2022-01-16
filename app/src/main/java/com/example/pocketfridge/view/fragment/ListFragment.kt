package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentListBinding
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.view.adapter.IngredientListAdapter
import com.example.pocketfridge.view.callback.CardClickCallback

/**
 * 食材一覧表示Fragment.
 */
class ListFragment(private val listData: List<Ingredient>) : Fragment() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientListFragment"
    }

    /** dataBinding. */
    private lateinit var binding: FragmentListBinding

    /** Adapter. */
    private val ingredientAdapter: IngredientListAdapter =
        IngredientListAdapter(object : CardClickCallback {
            override fun onCardClick(ingredient: Ingredient) {
                findNavController().navigate(R.id.action_tab_to_detail)
            }
        })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        Log.d(TAG, "onCreateView() called")
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = ingredientAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        ingredientAdapter.setIngredientList(listData)
    }
}