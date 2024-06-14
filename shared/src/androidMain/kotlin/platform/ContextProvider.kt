package platform

import android.content.Context
import androidx.startup.Initializer
import kotlin.properties.Delegates

internal var applicationContext: Context by Delegates.notNull()

class ContextProvider: Initializer<Unit> {

    override fun create(context: Context) {
        applicationContext = context.applicationContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}