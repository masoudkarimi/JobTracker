package data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import platform.applicationContext
import ir.masoudkarimi.data.db.JobTracker

actual class DatabaseDriver {
    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(
        schema = JobTracker.Schema,
        context = applicationContext,
        name = DB_NAME
    )
}

actual fun databaseDriver(): DatabaseDriver {
    return DatabaseDriver()
}