package platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ir.masoudkarimi.data.db.JobTracker

class IosDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = JobTracker.Schema,
        name = DB_NAME
    )
}