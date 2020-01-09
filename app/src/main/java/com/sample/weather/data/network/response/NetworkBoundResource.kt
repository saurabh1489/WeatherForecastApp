package com.sample.weather.data.network.response

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sample.weather.data.db.entity.CurrentWeatherEntity
import com.sample.weather.internal.ConnectivityException
import com.sample.weather.vi.Resource
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

abstract class NetworkBoundResource<RequestType, ResultType> : CoroutineScope {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        launch {
            setValue(Resource.Loading(null))
            val dbSource = loadFromDb()
            val data = awaitCallback<ResultType> {
                result.addSource(dbSource) { data ->
                    it.onComplete(data)
                }
            }
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                val newData = awaitCallback<ResultType> {
                    result.addSource(dbSource) { data ->
                        it.onComplete(data)
                    }
                }
                setValue(Resource.Success(newData))
            }
        }
    }

    interface Callback<T> {
        fun onComplete(result: T)
        fun onException(e: Exception?)
    }

    suspend fun <T> awaitCallback(block: (Callback<T>) -> Unit): T =
        suspendCancellableCoroutine { cont ->
            block(object : Callback<T> {
                override fun onComplete(result: T) = cont.resume(result)
                override fun onException(e: Exception?) {
                    e?.let { cont.resumeWithException(it) }
                }
            })
        }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        var apiResponse: LiveData<RequestType>
        try {
            apiResponse = createCall()
        } catch (exception: ConnectivityException) {
            apiResponse = MutableLiveData<RequestType>(null)
        }
        val newData = awaitCallback<ResultType> {
            result.addSource(dbSource) { data ->
                it.onComplete(data)
            }
        }
        setValue(Resource.Loading(newData))
        val response = awaitCallback<RequestType> {
            result.addSource(apiResponse) { response ->
                it.onComplete(response)
            }
        }
        result.removeSource(apiResponse)
        result.removeSource(dbSource)
        when (response) {
            is RequestType -> {
                saveCallResult(response)
                val dbSource = loadFromDb()
                val response = awaitCallback<ResultType> {
                    result.addSource(dbSource) { newData ->
                        it.onComplete(newData)
                    }
                }
                setValue(Resource.Success(response))
            }
            null -> {
                awaitCallback<ResultType> {
                    result.addSource(dbSource) { newData ->
                        it.onComplete(newData)
                    }
                }
                setValue(Resource.Error("No Connection Available"))
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected abstract suspend fun loadFromDb(): LiveData<ResultType>

    protected abstract suspend fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): LiveData<RequestType>

    protected abstract suspend fun saveCallResult(item: RequestType)
}