package platform

import android.content.Context
import androidx.startup.Initializer


class Initializer : Initializer<Unit> {

    override fun create(context: Context) {

    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}