package com.university.remote.domain.interactors

import com.university.remote.commons.exceptions.Failure
import com.university.remote.commons.response.Either
import com.university.remote.commons.usecase.BaseUseCase
import com.university.remote.domain.models.SpeedValueModel
import com.university.remote.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SendSpeedToWheelsUseCase(private val repository: Repository): BaseUseCase<SpeedValueModel, Any>() {

    override suspend fun execute(params: SpeedValueModel): Either<Failure, Any> {
        return try {
            withContext(Dispatchers.IO) { repository.sendSpeedToWheels(params) }
            Either.Right(0)
        }catch (e:Exception){
            if (e is IOException) {
                Either.Left(Failure.NetworkConnection)
            } else {
                Either.Left(SendValueToLeftWheelFailure(e))
            }
        }
    }

    data class SendValueToLeftWheelFailure(val error: Exception) : Failure.FeatureFailure(error)

}