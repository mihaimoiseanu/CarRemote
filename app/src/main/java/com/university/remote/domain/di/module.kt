package com.university.remote.domain.di

import com.university.remote.domain.interactors.SendSpeedToWheelsUseCase
import org.koin.dsl.module

val domainModule = module {

    single { SendSpeedToWheelsUseCase(get()) }

}