package com.university.remote.data.repository

import com.google.gson.Gson
import com.university.remote.data.net.WebSocketClient
import com.university.remote.domain.models.SpeedValueModel
import com.university.remote.domain.repository.Repository

class CarRemoteRepository(
    private val webSocketClient: WebSocketClient,
    private val gson: Gson
) : Repository {

    override suspend fun sendSpeedToWheels(value: SpeedValueModel) {
        val message = gson.toJson(value)
        webSocketClient.sendMessage(message)
    }

}