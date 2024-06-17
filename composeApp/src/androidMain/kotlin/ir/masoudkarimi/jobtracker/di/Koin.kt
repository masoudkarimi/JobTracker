package ir.masoudkarimi.jobtracker.di

import ir.masoudkarimi.jobtracker.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import platform.AndroidDatabaseDriverFactory
import platform.DatabaseDriverFactory

val androidModule = module {
    single<DatabaseDriverFactory> {
        AndroidDatabaseDriverFactory(androidContext())
    }

    viewModel { MainViewModel(get()) }
}