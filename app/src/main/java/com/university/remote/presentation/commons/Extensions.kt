package com.university.remote.presentation.commons

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

private fun <T> lazyUnsynchronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)

fun <ViewT : View> Activity.bindView(@IdRes idRes: Int): Lazy<ViewT> {
    return lazyUnsynchronized {
        findViewById<ViewT>(idRes)
    }
}