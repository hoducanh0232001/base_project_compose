package com.example.basecomposeproject.screen.splash

import com.example.core.viewmodel.ViewModel


class SplashViewModel(
) : ViewModel() {

    val splashEvent = EventFlow<SplashEvent>()

}

sealed interface SplashEvent {
    data class SetupCompleted(val isWaringShown: Boolean) : SplashEvent
}
