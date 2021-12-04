package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentInformationBinding
import com.example.pocketfridge.model.response.IngredientData

class InformationFragment : Fragment() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "InformationFragment"
    }

    /** view model. */

    /** viewBinding. */
    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!

    /** 修正データ. */
    private var fixData: IngredientData? = null


    /* -------------- life cycle ------------------ */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_info_to_list)
        }
    }
}