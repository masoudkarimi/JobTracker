package platform

import app.cash.sqldelight.db.SqlDriver

const val DB_NAME = "job_tracker_v2.db"

interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}


