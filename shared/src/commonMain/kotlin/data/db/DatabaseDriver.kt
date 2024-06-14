package data.db

import app.cash.sqldelight.db.SqlDriver

const val DB_NAME = "job_tracker.db"
expect class DatabaseDriver {
    fun createDriver(): SqlDriver
}

expect fun databaseDriver() : DatabaseDriver