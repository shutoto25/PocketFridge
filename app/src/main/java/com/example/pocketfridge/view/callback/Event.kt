package com.example.pocketfridge.view.callback

import androidx.lifecycle.Observer

/**
 * イベントを表すLiveDataを介して公開されるデータのラッパー.
 * https://github.com/google/iosched/blob/main/shared/src/main/java/com/google/samples/apps/iosched/shared/result/Event.kt
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /** contentを返して再度使用できないようにする.*/
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /** すでに処理されている場合でもcontentを返す.*/
    fun peekContent(): T = content
}

/**
 * [Event]のコンテンツがすでに処理されているかどうかをチェック.
 * [onEventUnhandledContent]は[Event]のコンテンツが処理されていない場合のみ呼び出される.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}