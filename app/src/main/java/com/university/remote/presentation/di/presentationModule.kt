package com.university.remote.presentation.di

import com.university.remote.presentation.carremote.CarRemoteViewModel
import com.university.remote.presentation.connection.ConnectionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        CarRemoteViewModel(get())
    }

    viewModel {
        ConnectionViewModel(get())
    }

}