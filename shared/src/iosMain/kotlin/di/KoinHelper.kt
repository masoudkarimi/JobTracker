package di

import data.ApplicationStatusRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class KoinHelper: KoinComponent {
    val applicationStatusRepository by inject<ApplicationStatusRepository>()
}

fun initKoin() {
    startKoin {
        modules(iosModule + commonModule)
    }
}