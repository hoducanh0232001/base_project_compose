package com.example.core.compose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun <T> EventEffect(
    event: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: suspend CoroutineScope.(T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(event, onEvent, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(state) {
            event.collect {
                launch {
                    onEvent(it)
                }
            }
        }
    }
}
