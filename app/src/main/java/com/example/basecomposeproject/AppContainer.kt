package com.example.basecomposeproject

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.basecomposeproject.compose.ScreenSurface
import com.example.basecomposeproject.screen.connection.LocalNoInternetState
import com.example.basecomposeproject.screen.connection.NoInternetDialog
import com.example.basecomposeproject.screen.connection.rememberNoInternetState
import com.example.basecomposeproject.screen.splash.SplashScreen
import com.example.core.compose.effect.EventEffect

import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppContainer() {
    val navController = rememberNavController()
    val viewModel = koinViewModel<MainViewModel>()

    val noInternetState = rememberNoInternetState()
    ScreenSurface {
        CompositionLocalProvider(
            LocalNoInternetState provides noInternetState,
        ) {
            NavHost(
                navController = navController,
                startDestination = Route.Splash,
                enterTransition = {
                    if (isNoAnim) {
                        fadeXIn()
                    } else {
                        sharedAxisXIn(initialOffsetX = { (it * INITIAL_OFFSET_FACTOR).toInt() })
                    }
                },
                exitTransition = {
                    if (isNoAnim) {
                        fadeXOut()
                    } else {
                        sharedAxisXOut(targetOffsetX = { -(it * INITIAL_OFFSET_FACTOR).toInt() })
                    }
                },
                popEnterTransition = {
                    if (isNoAnim) {
                        fadeXIn()
                    } else {
                        sharedAxisXIn(initialOffsetX = { -(it * INITIAL_OFFSET_FACTOR).toInt() })
                    }
                },
                popExitTransition = {
                    if (isNoAnim) {
                        fadeXOut()
                    } else {
                        sharedAxisXOut(targetOffsetX = { (it * INITIAL_OFFSET_FACTOR).toInt() })
                    }
                },
                modifier = Modifier.fillMaxSize(),
            ) {
                composable<Route.Splash> {
                    SplashScreen(navController)
                }



            }
        }


        val currentBackStackEntry by navController.currentBackStackEntryAsState()

        if (currentBackStackEntry?.isFirstRoute == false) {
            NoInternetDialog(state = noInternetState)
        }

        EventEffect(viewModel.connectionEvent) {
            when (it) {
                ConnectionEvent.ConnectionTimeout,
                ConnectionEvent.NoConnection,
                    -> {
                    noInternetState.showTimeout()
                }
            }
        }
    }
}

private const val ProgressThreshold = 0.35f
private const val INITIAL_OFFSET_FACTOR = 0.10f
private val Int.ForOutgoing: Int
    get() = (this * ProgressThreshold).toInt()

private val Int.ForIncoming: Int
    get() = this - this.ForOutgoing

private val AnimatedContentTransitionScope<NavBackStackEntry>.isNoAnim: Boolean
    get() = targetState.isNoAnim && initialState.isNoAnim

private val NavBackStackEntry.isNoAnim: Boolean
    get() = when {
        destination.hasRoute<Route.Menu>() -> true
        destination.hasRoute<Route.Splash>() -> true
        else -> false
    }

private val NavBackStackEntry.isFirstRoute: Boolean
    get() = when {
        destination.hasRoute<Route.AttentionUserApp>() -> true
        destination.hasRoute<Route.Splash>() -> true
        else -> false
    }

private val NavBackStackEntry.isMainRoute: Boolean
    get() = when {
        destination.hasRoute<Route.Menu>() -> true
        destination.hasRoute<Route.Setting>() -> true
        destination.hasRoute<Route.Operation>() -> true
        destination.hasRoute<Route.InfoMachine>() -> true
        destination.hasRoute<Route.Device>() -> true
        else -> false
    }

private fun sharedAxisXIn(
    initialOffsetX: (fullWidth: Int) -> Int,
    durationMillis: Int = NAV_ANIMATION_TIME,
): EnterTransition = slideInHorizontally(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing,
    ),
    initialOffsetX = initialOffsetX,
) + fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis.ForIncoming,
        delayMillis = durationMillis.ForOutgoing,
        easing = LinearOutSlowInEasing,
    ),
)

private fun sharedAxisXOut(
    targetOffsetX: (fullWidth: Int) -> Int,
    durationMillis: Int = NAV_ANIMATION_TIME,
): ExitTransition = slideOutHorizontally(
    animationSpec = tween(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing,
    ),
    targetOffsetX = targetOffsetX,
) + fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis.ForOutgoing,
        delayMillis = 0,
        easing = FastOutLinearInEasing,
    ),
)

private fun fadeXIn(
    durationMillis: Int = 1000,
): EnterTransition = fadeIn(
    animationSpec = tween(
        durationMillis = durationMillis.ForIncoming,
        delayMillis = 0,
        easing = FastOutLinearInEasing,
    ),
)

private fun fadeXOut(
    durationMillis: Int = 1500,
): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = durationMillis.ForOutgoing,
        delayMillis = 0,
        easing = FastOutLinearInEasing,
    ),
)

private const val NAV_ANIMATION_TIME = 300
