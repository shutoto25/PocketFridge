package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pocketfridge.databinding.FragmentIngredientListBinding

/**
 * 食材一覧表示Fragment.
 */
class IngredientListFragment : Fragment() {

    /** viewBinding. */
    private var _binding: FragmentIngredientListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentIngredientListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // メモリリーク対策のため、本契機でbindingを破棄する.
        _binding = null
    }

}