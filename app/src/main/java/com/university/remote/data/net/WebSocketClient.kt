package com.university.remote.data.net

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import timber.log.Timber

class WebSocketClient(
    private val okHttpClient: OkHttpClient
) {

    private lateinit var webSocket:WebSocket
    val connectionChannel  = Channel<ConnectionStatus>()

    fun connect(ip:String) = GlobalScope.launch{
        connectionChannel.send(ConnectionStatus.OPENING)
        val request = Request
            .Builder()
            .url("ws://${ip}:8802/websocket")
            .build()

        webSocket = okHttpClient.newWebSocket(request, CarRemoteWebSocketListener())
    }

    fun sendMessage(message:String){
        webSocket.send(message)
    }

    inner class CarRemoteWebSocketListener: WebSocketListener() {

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Timber.d("Socket closed: $reason")
            GlobalScope.launch {
                connectionChannel.send(ConnectionStatus.CLOSED)
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            Timber.d("Socket closed: $reason")
            GlobalScope.launch {
                connectionChannel.send(ConnectionStatus.CLOSING)
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Timber.e(t)
            GlobalScope.launch {
                connectionChannel.send(ConnectionStatus.FAILURE)
                delay(2_000)
                Timber.d("Try to reconnect")
                connectionChannel.send(ConnectionStatus.OPENING)
                this@WebSocketClient.webSocket = okHttpClient.newWebSocket(webSocket.request(), this@CarRemoteWebSocketListener)
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Timber.d("Messaged received: $text")
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Timber.d("Message received as bytes")
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Timber.d("Socket opened")
            GlobalScope.launch {
                connectionChannel.send(ConnectionStatus.OPENED)
            }
        }
    }
}