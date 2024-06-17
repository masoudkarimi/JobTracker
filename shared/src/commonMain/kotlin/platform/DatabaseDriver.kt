package platform

import app.cash.sqldelight.db.SqlDriver

const val DB_NAME = "job_tracker.db"

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}


