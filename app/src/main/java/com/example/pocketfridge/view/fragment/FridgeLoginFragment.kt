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
import com.example.pocketfridge.databinding.FragmentFridgeLoginBinding
import com.example.pocketfridge.utility.PrefUtil
import com.example.pocketfridge.view.callback.EventObserver
import com.example.pocketfridge.viewModel.FridgeLoginViewModel

/**
 * 冷蔵庫ログイン画面.
 */
class FridgeLoginFragment : Fragment() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "FridgeLoginFragment"
    }

    /** dataBinding. */
    private lateinit var binding: FragmentFridgeLoginBinding

    /** ViewModel. */
    private val viewModel by lazy {
        ViewModelProvider(this)[FridgeLoginViewModel::class.java]
    }

    /* -------------- life cycle ------------------ */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_fridge_login, container, false)

        binding.apply {
            viewModel = this@FridgeLoginFragment.viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        if (PrefUtil(requireContext()).getPrefString(PrefUtil.MY_FRIDGE_NAME) != null) {
            transitTab()
        } else {
            viewModel.onAccessDB.observe(this.viewLifecycleOwner, EventObserver {
                Log.d(TAG, "observed event = $it")
                when (it) {
                    // TODO エラーによってトースト文言分けるようにしたい.
                    "EVENT_ERROR" -> showErrorToast()
                    else -> transitTab()
                }
            })
        }
    }

    private fun transitTab() {
        findNavController().navigate(R.id.action_fridgeLogin_to_tab)
    }

    private fun showErrorToast() {

    }
}