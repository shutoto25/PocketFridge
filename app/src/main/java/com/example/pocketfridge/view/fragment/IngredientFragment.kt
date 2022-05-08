package com.example.pocketfridge.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentIngredientBinding
import com.example.pocketfridge.utility.DateUtil
import com.example.pocketfridge.view.callback.EventObserver
import com.example.pocketfridge.view.callback.IngredientDetailCallback
import com.example.pocketfridge.viewModel.IngredientViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 食材詳細画面.
 */
class IngredientFragment : Fragment() {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientFragment"
    }

    private val args: IngredientFragmentArgs by navArgs()

    /** ViewModel. */
    private val viewModel by lazy {
        ViewModelProvider(this)[IngredientViewModel::class.java]
    }

    /** Binding. */
    private lateinit var binding: FragmentIngredientBinding

    /** コールバック. */
    private val callback = object : IngredientDetailCallback {
        override fun onExpiredBoxClick() {
            Log.d(TAG, "onExpiredBoxClick() called")

            val calendar = Calendar.getInstance()
            // string→data→long
            DateUtil().stringToDate(viewModel.date.value)?.let { calendar.time = it }
            val selection = calendar.timeInMillis

            // 日付入力ピッカー.
            // TODO:bug 日付が1日前になってしまう
            // →MaterialDataPickerがUTC想定のため発生する問題。原因はわかったので一旦放置。
            // https://github.com/material-components/material-components-android/issues/714
            MaterialDatePicker.Builder.datePicker()
                .setSelection(selection).build().apply {
                    addOnPositiveButtonClickListener { time: Long ->
                        // long→date→string
                        viewModel.date.value = DateUtil().dateToString(Date(time))
                    }
                }.show(parentFragmentManager, "Tag")
        }

        override fun onRegisterClick() {
            Log.d(TAG, "onRegisterClick() called")
            if (viewModel.isValid()) {
                context?.let { viewModel.post(it) }
            } else {
                Snackbar.make(binding.root, "name is required.", Snackbar.LENGTH_SHORT).show()
            }
        }

        override fun onFixClick() {
            Log.d(TAG, "onFixClick() called")
            if (viewModel.isValid()) {
                context?.let { viewModel.put(it) }
            } else {
                Snackbar.make(binding.root, "name is required.", Snackbar.LENGTH_SHORT).show()
            }
        }

        override fun onDeleteClick() {
            Log.d(TAG, "onDeleteClick() called")
            deleteDialog()
        }
    }

    /* -------------- life cycle ------------------ */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() called")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient, container, false)
        binding.apply {
            lifecycleOwner = this@IngredientFragment
            isAdd = true
            args.ingredient?.also {
                this@IngredientFragment.viewModel.setData(it)
                isAdd = false
            }
            viewModel = this@IngredientFragment.viewModel
            callback = this@IngredientFragment.callback
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
        // 画面遷移イベントオブザーバー.
        viewModel.onTransit.observe(viewLifecycleOwner, EventObserver {
            // 前の画面に戻る.
            findNavController().navigate(R.id.action_detail_to_tab)
        })
    }

    private fun deleteDialog() {
        MaterialAlertDialogBuilder(requireContext(),
            R.style.ThemeOverlay_Material3_MaterialAlertDialog)
            .setTitle("DELETE")
            .setMessage("この食材データを削除しますか？")
            .setIcon(R.drawable.ic_search)
            .setNegativeButton("cancel") { _, _ ->
            }
            .setPositiveButton("accept") { _, _ ->
                context?.let { viewModel.delete(it) }
            }.show()
    }
}