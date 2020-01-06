package com.university.remote.presentation.carremote

import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar

abstract class SeekBarTouchListener : View.OnTouchListener {
    /**
     * `true` if the thumb is being dragged; `false` otherwise.
     */
    private var isDragging = false

    private var lastProgress = 50

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (v !is SeekBar) return true

        if (!v.isEnabled || v.thumb == null) {
            return true
        }

        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!isWithinThumb(v.thumb, event)) {
                    return true
                }
                isDragging = true
                false
            }
            MotionEvent.ACTION_UP -> {
                v.post {
                    onChangedFinished()
                    v.progress = 50
                }
                isDragging = false
                false
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isDragging) {
                    return true
                }
                if (lastProgress != v.progress) {
                    onSeekBarProgressChanged(v.progress)
                    lastProgress = v.progress
                }
                false
            }
            MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                false
            }
            else -> false
        }
    }

    private fun isWithinThumb(thumb: Drawable, event: MotionEvent): Boolean {
        return thumb.bounds.contains(event.x.toInt(), event.y.toInt())
    }

    abstract fun onSeekBarProgressChanged(progress: Int)

    abstract fun onChangedFinished()
}