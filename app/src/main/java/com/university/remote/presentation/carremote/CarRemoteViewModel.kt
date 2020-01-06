package com.university.remote.presentation.carremote

import androidx.lifecycle.viewModelScope
import com.university.remote.domain.interactors.SendSpeedToWheelsUseCase
import com.university.remote.domain.models.SpeedValueModel
import com.university.remote.presentation.commons.BaseViewModel

class CarRemoteViewModel(
    private val sendSpeedToWheelsUseCase: SendSpeedToWheelsUseCase
) : BaseViewModel() {


    fun sendValueToWheels(left:Int, right:Int){
        val model = SpeedValueModel(left, right)
        sendSpeedToWheelsUseCase(
            viewModelScope,
            model
        ){
            it.either(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(value: Any) {
    }
}