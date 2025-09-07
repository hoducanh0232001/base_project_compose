package com.example.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.flow.update as mutableUpdate

abstract class ViewModel : androidx.lifecycle.ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        onException(exception)
    }

    protected fun CoroutineScope.safeLaunch(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return launch(context + exceptionHandler, block = block)
    }

    protected open fun onException(exception: Throwable) {
        Timber.e(exception)
    }

    protected fun <T> EventFlow<T>.send(event: T) {
        (this as? EventChannelFlow<T>)?.send(event)
    }

    protected fun <T> StateFlow(value: T): StateFlow<T> {
        return MutableStateFlow(value)
    }

    protected inline fun <T> StateFlow<T>.update(function: (T) -> T) {
        if (this is MutableStateFlow<T>) {
            mutableUpdate(function)
        }
    }

    protected fun <T> EventFlow(): EventFlow<T> = EventChannelFlow()
}

private class EventChannelFlow<T> : EventFlow<T> {
    private val channel = Channel<T>(capacity = Channel.BUFFERED)

    override suspend fun collect(collector: FlowCollector<T>) {
        channel.receiveAsFlow().collect(collector = collector)
    }

    fun send(event: T) {
        channel.trySend(event)
    }
}

private val applicationCoroutineScope: CoroutineScope by lazy {
    CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
}
val ViewModel.applicationScope: CoroutineScope
    get() = applicationCoroutineScope

interface EventFlow<T> : Flow<T>
