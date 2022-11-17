package com.example.noteapp.core

import kotlinx.coroutines.flow.flow
import java.io.IOException

abstract class BaseRepository {

    protected fun <T> doRequest(request: suspend () -> T) = flow {
        emit(Resource.Loading(null))
        try {
            val data = request()
            emit(Resource.Success(data))
        } catch (ioException: IOException) {
            emit(Resource.Error(ioException.localizedMessage?: "Неизвестная ошибка"))
        }
    }
}