package com.example.basecomposeproject.screen.connection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.res.stringResource
import com.example.basecomposeproject.R
import com.example.basecomposeproject.compose.ConfirmNoTitleDialog

@Stable
class NoInternetState(visible: Boolean = false) {
    var isVisible by mutableStateOf(visible)
        private set

    private var isAllowShowNoInternet = false

    fun open() {
        isVisible = true
    }

    fun close() {
        isVisible = false
    }

    fun showTimeout() {
        if (isAllowShowNoInternet) {
            open()
        }
    }

    fun setAllowShowNoInternet(isAllowShowNoInternet: Boolean) {
        this.isAllowShowNoInternet = isAllowShowNoInternet
    }

    companion object {
        val Saver: Saver<NoInternetState, *> = listSaver(
            save = { listOf(it.isVisible) },
            restore = { NoInternetState(it[0]) },
        )
    }
}

@Composable
fun rememberNoInternetState(): NoInternetState {
    return rememberSaveable(saver = NoInternetState.Saver) {
        NoInternetState()
    }
}

val LocalNoInternetState = staticCompositionLocalOf { NoInternetState() }

@Composable
fun NoInternetDialog(
    state: NoInternetState,
) {
    if (state.isVisible) {
        ConfirmNoTitleDialog(
            content = stringResource(R.string.no_internet_connection),
            onCancelClick = { state.close() },
            onConfirmClick = {
                state.close()
            },
            onDismissRequest = {
                state.close()
            },
        )
    }
}