package com.example.pocketfridge.utility

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Nullable

/**
 * プリファレンス ユーティリティクラス.
 */
class PrefUtil(private val context: Context) {

    companion object {
        /** プリファレンスの名前 */
        private const val PREF_NAME = "app_preferences"
        // ここに定数を書いていく
        const val MY_FRIDGE_ID = "my_fridge_id"
        const val MY_FRIDGE_NAME = "my_fridge_name"
        const val MY_FRIDGE_PASSWORD = "my_fridge_password"
    }

    /** String型を保存 */
    fun put(key: String, value: String) {
        writePref().putString(key, value).apply()
    }

    /** int型を保存 */
    fun put(key: String, value: Int) {
        writePref().putInt(key, value).apply()
    }

    /** boolean型を保存 */
    fun put(key: String, value: Boolean) {
        writePref().putBoolean(key, value).apply()
    }

    /** float型を保存 */
    fun put(key: String, value: Float) {
        writePref().putFloat(key, value).apply()
    }

    /** long型を保存 */
    fun put(key: String, value: Long) {
        writePref().putLong(key, value).apply()
    }

    /** String型を取得 */
    fun getPrefString(key: String, @Nullable defValue: String? = null): String? = readPref().getString(key, defValue)


    /** int型を取得 */
    fun getPrefInt(key: String, @Nullable defValue: Int = 0): Int = readPref().getInt(key, defValue)


    /** boolean型を取得 */
    fun getPrefBool(key: String, defValue: Boolean): Boolean = readPref().getBoolean(key, defValue)


    /** float型を取得 */
    fun getPrefFloat(key: String, defValue: Float): Float = readPref().getFloat(key, defValue)


    /** long型を取得 */
    fun getPrefLong(key: String, defValue: Long): Long = readPref().getLong(key, defValue)

    /** Preference Key削除 */
    fun removePref(key: String) = writePref().remove(key).commit()

    /** Preference取得 */
    private fun readPref(): SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /** Preference書き込みEditor取得 */
    private fun writePref(): SharedPreferences.Editor {
        return readPref().edit()
    }

}