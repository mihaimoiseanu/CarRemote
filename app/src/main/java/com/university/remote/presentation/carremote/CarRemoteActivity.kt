package com.university.remote.presentation.carremote

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSeekBar
import com.university.remote.R
import com.university.remote.presentation.commons.bindView
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarRemoteActivity : AppCompatActivity() {

    private val carRemoteViewModel by viewModel<CarRemoteViewModel>()
    private val left by bindView<SeekBar>(R.id.left)
    private val right by bindView<SeekBar>(R.id.right)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        left.setOnTouchListener(object: SeekBarTouchListener(){
            override fun onSeekBarProgressChanged(progress: Int) {
                sendSpeed(progress, right.progress)
            }

            override fun onChangedFinished() {
                sendSpeed(50, right.progress)
            }
        })

        right.setOnTouchListener(object: SeekBarTouchListener(){
            override fun onSeekBarProgressChanged(progress: Int) {
                sendSpeed(left.progress, progress)
            }

            override fun onChangedFinished() {
                sendSpeed(left.progress, 50)
            }
        })
    }

    private fun sendSpeed(left:Int, right:Int){
        val actualLeft = (left - 50) * 8
        val actualRight = (right - 50) * 8
        carRemoteViewModel.sendValueToWheels(actualLeft, actualRight)
    }
}
