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
import androidx.navigation.fragment.navArgs
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentIngredientBinding
import com.example.pocketfridge.view.callback.IngredientDetailCallback
import com.example.pocketfridge.view.callback.NavigationBack
import com.example.pocketfridge.viewModel.IngredientViewModel
import com.google.android.material.datepicker.MaterialDatePicker
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

        private const val DATE_FORMAT = "yyyy/MM/dd"
    }

    private val navigationBack = object : NavigationBack {
        override fun onBack(result: Boolean) {
            // 前の画面に戻る.
            findNavController().navigate(R.id.action_detail_to_tab)
        }
    }

    private val args: IngredientFragmentArgs by navArgs()
    private var longDate: Long = 0
    private val format = SimpleDateFormat(DATE_FORMAT, Locale.JAPAN)

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
            val selection = if (binding.date.text.isNullOrEmpty()) {
                calendar.timeInMillis
            } else {
                val date = stringToDate(binding.date.text.toString())
                calendar.time = date!!
                calendar.timeInMillis
            }
            // 日付入力ピッカー. TODO 日付がおかしい、1日前になる
            MaterialDatePicker.Builder.datePicker()
                .setSelection(selection).build().apply {
                    addOnPositiveButtonClickListener { time: Long ->
                        longDate = time
                        binding.date.setText(format.format(Date(time)))
                    }
                }.show(parentFragmentManager, "Tag")
        }

        override fun onRegisterClick() {
            Log.d(TAG, "onRegisterClick() called")
            viewModel.post(navigationBack)
//           Snackbar.make(view, "入力が不足しています", Snackbar.LENGTH_SHORT).show()
        }

        override fun onFixClick() {
            Log.d(TAG, "onFixClick() called")
            viewModel.put(navigationBack)
//           Snackbar.make(view, "入力が不足しています", Snackbar.LENGTH_SHORT).show()
        }

        override fun onDeleteClick() {
            Log.d(TAG, "onDeleteClick() called")
            // TODO 削除ダイアログくらいは出しておいた方が親切.
            viewModel.delete(navigationBack)
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

    /* -------------- converter ------------------ */
    fun stringToDate(dateStr: String): Date? {
        var date: Date? = null
        try {
            date = format.parse(dateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }
}