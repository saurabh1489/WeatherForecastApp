package com.sample.weather.internal

import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(Dispatchers.Main, start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}