package com.example.pocketfridge.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.pocketfridge.databinding.ActivityAddBinding
import com.example.pocketfridge.model.repsitory.IngredientRepository
import com.example.pocketfridge.model.response.IngredientData
import com.example.pocketfridge.model.response.IngredientResponse
import com.example.pocketfridge.utility.PrefUtil
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import rx.Observer
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        /** ログ出力タグ. */
        private const val TAG = "AddActivity"

        private const val DATE_FORMAT = "yyyy/MM/dd"
    }

    private var date: Long = 0
    private val format = SimpleDateFormat(DATE_FORMAT, Locale.JAPAN)

    /** viewBinding. */
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.date.setOnClickListener(this)
        binding.fabAdd.setOnClickListener(this)
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
                            date
                        }
                // 日付入力ピッカー.
                MaterialDatePicker.Builder.datePicker().setSelection(selection)
                        .build().apply {
                            addOnPositiveButtonClickListener { time: Long ->
                                date = time
                                binding.date.setText(format.format(Date(time)))
                            }
                        }.show(supportFragmentManager, "Tag")
            }

            // 追加ボタン.
            binding.fabAdd -> {
                val requestBody = createRequestBody()
                requestBody?.let {
                    IngredientRepository().post(createObserver(), requestBody)
                } ?: Snackbar.make(view, "入力が不足しています", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /** POSTデータ生成. */
    private fun createRequestBody(): IngredientData? {
        Log.d(TAG, "createRequestBody() called")

        if (binding.name.text.isNullOrEmpty()) {
            return null
        }

        var genreId = 0
        binding.chipGroup.children
                .toList()
                .filter { (it as Chip).isChecked }
                .forEach { genreId = it.id - 1 } // なぜか先頭が2で取れるので調整.

        return IngredientData(
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
            finish()
        }

        override fun onError(error: Throwable?) {
            Log.d(TAG, "Observer.onError() called with: error = $error")

        }

        override fun onCompleted() {
            Log.d(TAG, "Observer.onCompleted() called")
        }
    }

}