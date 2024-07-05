package ir.masoudkarimi.jobtracker.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.masoudkarimi.jobtracker.ui.home.HomeScreen

@Composable
fun JobTrackerApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier) { paddings ->
        NavHost(
            modifier = Modifier
                .padding(paddings)
                .consumeWindowInsets(paddings),
            navController = navController,
            startDestination = Home
        ) {
            composable<Home> {
                HomeScreen()
            }
        }
    }
}