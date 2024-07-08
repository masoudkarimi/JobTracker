package ir.masoudkarimi.jobtracker.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.masoudkarimi.jobtracker.ui.component.ApplicationStatus
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        val screenWidth = LocalConfiguration.current.screenWidthDp
        val columnWidth = screenWidth / 1.5

        LazyRow(
            modifier = modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(uiState.applications, key = { _, item -> item.statusId }) { index, item ->
                LazyColumn(
                    modifier = Modifier
                        .width(columnWidth.dp)
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    stickyHeader {
                        ApplicationStatus(applicationStatus = item)
                    }

                    items(item.jobs) { job ->
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = job.jobTitle)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = job.createdAt)
                        }
                    }

                    item {
                        Button(onClick = { viewModel.addJob(item) }) {
                            Text(text = "Add New Job")
                        }
                    }
                }
            }

            item {
                Button(onClick = viewModel::addNewColumnClicked) {
                    Text(text = "Add Column")
                }
            }
        }
    }
}