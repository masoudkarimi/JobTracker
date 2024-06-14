package data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ir.masoudkarimi.data.db.JobTracker

actual class DatabaseDriver {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = JobTracker.Schema,
        name = DB_NAME
    )
}

actual fun databaseDriver(): DatabaseDriver {
    return DatabaseDriver()
}