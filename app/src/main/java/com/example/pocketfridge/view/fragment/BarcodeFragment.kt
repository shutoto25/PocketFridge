package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentBarcodeBinding
import com.example.pocketfridge.view.callback.EventObserver
import com.example.pocketfridge.viewModel.CameraViewModel

/**
 *
 */
class BarcodeFragment : Fragment() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "BarcodeFragment"
    }

    /** ViewModel. */
    private val viewModel by lazy {
        ViewModelProvider(this)[CameraViewModel::class.java]
    }

    /** Binding. */
    private lateinit var binding: FragmentBarcodeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_barcode, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        // 画面遷移イベントオブザーバー.
        viewModel.onEvent.observe(viewLifecycleOwner, EventObserver {
            // 前の画面に戻る.
            findNavController().navigate(R.id.action_barcode_to_tab)
        })
    }
}