package di

import org.koin.dsl.module
import platform.DatabaseDriverFactory
import platform.IosDatabaseDriverFactory

val iosModule = module {
    single<DatabaseDriverFactory> {
        IosDatabaseDriverFactory()
    }
}