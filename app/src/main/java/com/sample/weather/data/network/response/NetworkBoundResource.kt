package com.sample.weather.data.network.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.sample.weather.internal.ConnectivityException
import com.sample.weather.vi.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<RequestType, ResultType> : CoroutineScope {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        launch {
            result.value = Resource.Loading(null)
            val dbSource = loadFromDb()
            result.addSource(dbSource) { data ->
                launch {
                    result.removeSource(dbSource)
                    if (shouldFetch(data)) {
                        fetchFromNetwork(dbSource)
                    } else {
                        result.addSource(dbSource) { newData ->
                            setValue(Resource.Success(newData))
                        }
                    }
                }
            }
        }
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
        result.addSource(dbSource) { newData ->
            setValue(Resource.Loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is RequestType -> {
                    launch {
                        withContext(Dispatchers.IO) {
                            saveCallResult(response)
                        }
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.Success(newData))
                        }
                    }
                }
                null -> {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.Error("No Connection Available"))
                    }
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected abstract suspend fun loadFromDb(): LiveData<ResultType>

    protected abstract suspend fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): LiveData<RequestType>

    protected abstract suspend fun saveCallResult(item: RequestType)
}