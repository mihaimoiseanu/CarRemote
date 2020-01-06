package com.university.remote.domain.repository

import com.university.remote.domain.models.SpeedValueModel

interface Repository {

    suspend fun sendSpeedToWheels(value:SpeedValueModel)

}