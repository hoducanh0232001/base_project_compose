package com.example.basecomposeproject.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.basecomposeproject.R
import com.example.basecomposeproject.Route
import com.example.core.compose.effect.EventEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    navController: NavController,
) {
    val viewModel = koinViewModel<SplashViewModel>()

    EventEffect(viewModel.splashEvent) {
        when (it) {
            is SplashEvent.SetupCompleted -> {
                if (it.isWaringShown) {
                    navController.navigate(Route.AttentionUserApp) {
                        popUpTo(Route.Splash) {
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate(Route.Menu) {
                        popUpTo(Route.Splash) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
    SplashContent(modifier = Modifier.fillMaxSize())
}

@Composable
private fun SplashContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 78.dp)
                .align(alignment = Alignment.Center),
        )
    }
}

@Preview
@Composable
private fun PreviewSplash() {
    SplashContent()
}
