package com.example.pocketfridge.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pocketfridge.AppConst
import com.example.pocketfridge.R
import com.example.pocketfridge.databinding.FragmentIngredientBinding
import com.example.pocketfridge.model.data.Ingredient
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.model.response.IngredientResponse
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import rx.Observer
import java.text.SimpleDateFormat
import java.util.*

/**
 * 食材詳細画面.
 */
class IngredientFragment : Fragment(),
    View.OnClickListener {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "IngredientFragment"

        private const val DATE_FORMAT = "yyyy/MM/dd"
    }
//    TODO fragment navigationで値を渡せるようにしたいが、
//     viewModelのせいでrebuildが通らなくてできないので後に回す
//    private val args: IngreientFragmentArgs by navArgs()
    private var longDate: Long = 0
    private val format = SimpleDateFormat(DATE_FORMAT, Locale.JAPAN)

    /** Binding. */
    private lateinit var binding: FragmentIngredientBinding

    /** 修正データ. */
    private var fix: Ingredient? = null


    /* -------------- life cycle ------------------ */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredient, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")

        // 修正画面時のみデータを設定.
        fix?.let {
            binding.name.setText(it.name)
            when (it.genre) {
                "1" -> binding.chipChildMeat.isChecked = true
                "2" -> binding.chipChildVegetable.isChecked = true
                "3" -> binding.chipChildFish.isChecked = true
                "4" -> binding.chipChildRefrigerate.isChecked = true
                "5" -> binding.chipChildFrozen.isChecked = true
                "6" -> binding.chipChildOthers.isChecked = true
            }
            it.useByDate?.let { date ->
                binding.date.setText(format.format(date))
                longDate = date.time
            }
            it.left?.let { left ->
                binding.percent.value = left.toFloat()
            }

            // 修正時画面用にボタンレイアウト変更.
            binding.fabAdd.visibility = View.GONE
            binding.fabFix.visibility = View.VISIBLE
            binding.fabDelete.visibility = View.VISIBLE
        }
    }


    /* -------------- UI ------------------ */
    private fun setListeners() {
        Log.d(TAG, "setListeners() called")
        binding.date.setOnClickListener(this)
        binding.fabAdd.setOnClickListener(this)
        binding.fabDelete.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "onClick() called with: view = $view")

        when (view) {
            // 日付.
            binding.date -> {
                val selection =
                    if (binding.date.text.isNullOrEmpty()) {
                        Calendar.getInstance().timeInMillis
                    } else {
                        longDate
                    }
                // 日付入力ピッカー.
                MaterialDatePicker.Builder.datePicker().setSelection(selection)
                    .build().apply {
                        addOnPositiveButtonClickListener { time: Long ->
                            longDate = time
                            binding.date.setText(format.format(Date(time)))
                        }
                    }.show(parentFragmentManager, "Tag")
            }

            // 追加ボタン.
            binding.fabAdd -> {
                findNavController().navigate(R.id.action_detail_to_tab)
                val requestBody = createRequestBody()
                requestBody?.let {
//                    IngredientRepository().post(createObserver(), requestBody)
                } ?: Snackbar.make(view, "入力が不足しています", Snackbar.LENGTH_SHORT).show()
            }

            // 修正ボタン.
            binding.fabFix -> {
                val requestBody = createRequestBody()
                requestBody?.let {
//                    IngredientRepository().post(createObserver(), requestBody)
                } ?: Snackbar.make(view, "入力が不足しています", Snackbar.LENGTH_SHORT).show()
            }

            // 削除ボタン.
            binding.fabDelete -> {
//                IngredientRepository().delete(createObserver(), fixData!!.id)
                val intent = Intent().apply { putExtra(AppConst.INTENT_FLAG_REQUEST, true) }
            }
        }
    }

    /** 編集終了. */
    private fun finishEdit(isUpdate: Boolean) {
        if (isUpdate) {

        } else {

        }
    }

    /* -------------- server connection ------------------ */
    /** POSTデータ生成. */
    private fun createRequestBody(): Ingredient? {
        Log.d(TAG, "createRequestBody() called")

        if (binding.name.text.isNullOrEmpty()) {
            return null
        }

        var genreId = 0
        binding.chipGroup.children
            .toList()
            .filter { (it as Chip).isChecked }
            .forEach { genreId = it.id - 1 } // TODO なぜか先頭が2で取れるので調整.

        // TODO 追加時はid=0とかでいいか？
        return Ingredient(
            0,
            binding.name.text.toString(),
            genreId.toString(),
            null,
            null,
            binding.percent.value.toInt()
        )
    }

    /** observer作成. */
    private fun createObserver() = object : Observer<IngredientResponse> {
        override fun onNext(response: IngredientResponse) {
            Log.d(TAG, "Observer.onNext() called with: response = $response")
            val intent = Intent().apply { putExtra(AppConst.INTENT_FLAG_REQUEST, true) }
        }

        override fun onError(error: Throwable?) {
            Log.d(TAG, "Observer.onError() called with: error = $error")
            Snackbar.make(binding.root, "登録に失敗しました", Snackbar.LENGTH_SHORT).show()
        }

        override fun onCompleted() {
            Log.d(TAG, "Observer.onCompleted() called")
        }
    }
}