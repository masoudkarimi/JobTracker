package ir.masoudkarimi.jobtracker

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import data.db.Database
import data.db.provideDatabase
import kotlinx.coroutines.launch

class MainViewModel(
    private val database: Database
) : ViewModel() {

    init {
        viewModelScope.launch {
            database.getAllApplicationStatusesList().ifEmpty {
                Log.d("MainViewModel", "Application Statuses are empty. Add some")
                database.insertNewApplicationStatus("Wishlist")
                database.insertNewApplicationStatus("Applied")
                database.insertNewApplicationStatus("Interview")
                database.insertNewApplicationStatus("Offer")
                database.insertNewApplicationStatus("Rejected")
            }
        }

        viewModelScope.launch {
            database.getAllApplicationStatuses().collect {
                Log.d("MainViewModel", "Application Statuses: $it")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(provideDatabase())
            }
        }
    }
}