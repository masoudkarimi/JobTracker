package ir.masoudkarimi.jobtracker

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ApplicationStatusRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val applicationStatusRepository: ApplicationStatusRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            applicationStatusRepository.flow.collect {
                it.ifEmpty {
                    Log.d("MainViewModel", "Application Statuses are empty. Add some")
                    addSomeApplicationStatus()
                }
            }
        }

        viewModelScope.launch {
            applicationStatusRepository.flow.collect {
                Log.d("MainViewModel", "Application Statuses: $it")
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
}