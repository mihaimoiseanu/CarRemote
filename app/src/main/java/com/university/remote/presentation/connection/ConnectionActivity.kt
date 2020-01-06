package com.university.remote.presentation.connection

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.university.remote.R
import com.university.remote.presentation.carremote.CarRemoteActivity
import com.university.remote.presentation.commons.bindView

import kotlinx.android.synthetic.main.activity_connection.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectionActivity : AppCompatActivity() {

    private val viewModel by viewModel<ConnectionViewModel>()
    private val ip by bindView<EditText>(R.id.ip)
    private val button by bindView<Button>(R.id.connect)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        button.setOnClickListener {
            val ipString = ip.text.toString()
            if(TextUtils.isEmpty(ipString)){
                ip.error = "Insert something"
                return@setOnClickListener
            }
            viewModel.connect(ipString)
        }

        viewModel.onConnectionOpened.observe(this, Observer {
            if(it){
                Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, CarRemoteActivity::class.java))
            }
        })
    }

}
