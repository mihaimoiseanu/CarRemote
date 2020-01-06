package com.university.remote.presentation.commons

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.university.remote.commons.exceptions.Failure

open class BaseViewModel : ViewModel() {
    var failure = MutableLiveData<Failure>()

    fun handleFailure(failure: Failure) {
        this.failure.postValue(failure)
        Log.e("ViewModel", failure.toString())
    }
}