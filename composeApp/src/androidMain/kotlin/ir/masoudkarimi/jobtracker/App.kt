package ir.masoudkarimi.jobtracker

import android.app.Application
import di.commonModule
import ir.masoudkarimi.jobtracker.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App.applicationContext)
            modules(commonModule + androidModule)
        }
    }
}