package com.example.pocketfridge.view.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * xmlで使用できるオリジナルの属性の定義と独自の処理の実装を行う.
 */
object CustomBindingAdapter {
    @BindingAdapter("isVisible")
    @JvmStatic
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @BindingAdapter("formatDate")
    @JvmStatic
    fun TextView.setDate(date: Date?) {
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN)
        try {
            date?.let {
                this.text = format.format(date)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}