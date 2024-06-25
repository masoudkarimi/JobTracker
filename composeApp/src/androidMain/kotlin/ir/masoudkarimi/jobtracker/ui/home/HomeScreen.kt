package ir.masoudkarimi.jobtracker.ui.home

import Greeting
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ir.masoudkarimi.jobtracker.MainViewModel
import jobtracker.composeapp.generated.resources.Res
import jobtracker.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@Composable
@Preview
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = koinViewModel()
) {
    var showContent by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }

}