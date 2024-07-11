package ir.masoudkarimi.jobtracker.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ApplicationStatusRepository
import data.model.ApplicationStatus
import data.model.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

class HomeViewModel(
    private val applicationStatusRepository: ApplicationStatusRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            applicationStatusRepository.flow.collect {
                it.ifEmpty {
                    addSomeApplicationStatus()
                }
            }
        }

        viewModelScope.launch {
            applicationStatusRepository.flow
                .onStart {
                    _uiState.update { it.copy(isLoading = true) }
                    delay(1000)
                }
                .collect { applications ->
                    applications.forEach {
                        Log.d("Applications", it.toString())
                    }
                    _uiState.update { it.copy(isLoading = false, applications = applications) }
                }
        }
    }

    private fun addSomeApplicationStatus() {
        viewModelScope.launch {
            applicationStatusRepository.insert("Wishlist")
            applicationStatusRepository.insert("Applied")
            applicationStatusRepository.insert("Interview")
            applicationStatusRepository.insert("Offer")
            applicationStatusRepository.insert("Rejected")
        }
    }

    fun addNewColumnClicked() {
        val latestColumn = uiState.value.applications.last()
        viewModelScope.launch {
            applicationStatusRepository.insert("Sample Column ${latestColumn.statusPosition - 4}")
        }
    }

    fun addJob(item: ApplicationStatus) {
        viewModelScope.launch {
            val currentJobs = item.jobs
            applicationStatusRepository.insertJob(
                Job(
                    id = 0,
                    companyName = "Company Name ${currentJobs.lastIndex + 1}",
                    jobTitle = "Job Title ${Random.nextInt(0, 1000)}",
                    salary = Random.nextInt(3000, 10000).toString(),
                    link = "sample.org",
                    statusId = item.statusId,
                    location = "Netherlands",
                    createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
            )
        }
    }
}