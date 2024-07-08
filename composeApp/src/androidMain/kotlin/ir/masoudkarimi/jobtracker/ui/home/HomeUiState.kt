package ir.masoudkarimi.jobtracker.ui.home

import data.model.ApplicationStatus

data class HomeUiState(
    val isLoading: Boolean = false,
    val applications: List<ApplicationStatus> = emptyList()
)