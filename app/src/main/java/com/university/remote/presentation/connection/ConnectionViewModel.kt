package com.university.remote.presentation.connection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.university.remote.data.net.ConnectionStatus
import com.university.remote.data.net.WebSocketClient
import com.university.remote.presentation.commons.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class ConnectionViewModel(private val client: WebSocketClient) : BaseViewModel() {

    val onConnectionOpened = MutableLiveData<Boolean>()

    fun connect(ip: String) {
        client.connect(ip)
        viewModelScope.launch {
            client.connectionChannel.consumeEach {
                if (ConnectionStatus.OPENED == it) {
                    onConnectionOpened.postValue(true)
                }
            }
        }
    }
}