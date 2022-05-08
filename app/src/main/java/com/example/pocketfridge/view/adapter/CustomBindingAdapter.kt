package com.example.pocketfridge.view.adapter

import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.pocketfridge.R
import com.example.pocketfridge.model.data.GenreType
import com.example.pocketfridge.utility.DateUtil
import com.google.android.material.chip.ChipGroup
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

/**
 * xmlで使用できるオリジナルの属性の定義と独自の処理の実装を行う.
 */
object CustomBindingAdapter {
    /** ボタンVisibility判定. */
    @JvmStatic
    @BindingAdapter("isVisible")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    /* -------------- ChipGroup ------------------ */
    @JvmStatic
    @BindingAdapter("chipGroupChecked")
    fun chipGroupValue(chipGroup: ChipGroup, id: Int) {
        when (id) {
            GenreType.MEAT.id -> chipGroup.check(R.id.chip_child_meat)
            GenreType.VEGETABLE.id -> chipGroup.check(R.id.chip_child_vegetable)
            GenreType.FISH.id -> chipGroup.check(R.id.chip_child_fish)
            GenreType.REFRIGERATE.id -> chipGroup.check(R.id.chip_child_refrigerate)
            GenreType.FROZEN.id -> chipGroup.check(R.id.chip_child_frozen)
            GenreType.OTHER.id -> chipGroup.check(R.id.chip_child_others)
            else -> {}
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "chipGroupChecked")
    fun chipGroupValueInverse(chipGroup: ChipGroup): Int {
        return when (chipGroup.checkedChipId) {
            R.id.chip_child_meat -> GenreType.MEAT.id
            R.id.chip_child_vegetable -> GenreType.VEGETABLE.id
            R.id.chip_child_fish -> GenreType.FISH.id
            R.id.chip_child_refrigerate -> GenreType.REFRIGERATE.id
            R.id.chip_child_frozen -> GenreType.FROZEN.id
            R.id.chip_child_others -> GenreType.OTHER.id
            else -> GenreType.ALL.id
        }
    }

    @JvmStatic
    @BindingAdapter("chipGroupCheckedAttrChanged")
    fun chipGroupValueListener(
        chipGroup: ChipGroup, chipGroupValueAttrChange: InverseBindingListener?
    ) {
        chipGroup.setOnCheckedChangeListener { _, _ ->
            chipGroupValueAttrChange?.onChange()
        }
    }

    /* -------------- Slider ------------------ */
    /** Model --> View */
    @JvmStatic
    @BindingAdapter("sliderValue")
    fun sliderValue(slider: Slider, value: Int) {
        // 無限ループ防止チェック.
        if (slider.value != value.toFloat()) {
            slider.value = value.toFloat()
        }
    }

    /** View --> Model */
    @JvmStatic
    @InverseBindingAdapter(attribute = "sliderValue")
    fun sliderValueInverse(slider: Slider): Int {
        return slider.value.toInt()
    }

    /** 変更時にbindSliderValueInverse()を呼び出す */
    @JvmStatic
    @BindingAdapter("sliderValueAttrChanged")
    fun sliderListener(slider: Slider, sliderValueAttributeChange: InverseBindingListener?) {
        slider.addOnChangeListener { _, _, _ ->
            sliderValueAttributeChange?.onChange()
        }
    }
}