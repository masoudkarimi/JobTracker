package di

import data.ApplicationStatusRepository
import data.ApplicationStatusRepositoryImpl
import ir.masoudkarimi.data.db.JobTracker
import org.koin.dsl.module
import platform.DatabaseDriverFactory

val commonModule = module {
    single<JobTracker> {
        JobTracker(get<DatabaseDriverFactory>().createDriver())
    }

    factory<ApplicationStatusRepository> {
        ApplicationStatusRepositoryImpl(get())
    }
}