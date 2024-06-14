package data.db

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import data.db.model.ApplicationStatusEntity
import ir.masoudkarimi.data.db.JobTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

fun provideDatabase(): Database {
    return Database(databaseDriver())
}

class Database(private val databaseDriver: DatabaseDriver) {
    private val database = JobTracker(databaseDriver.createDriver())
    private val dbQuery = database.jobTrackerQueries

    fun getAllApplicationStatuses(): Flow<List<ApplicationStatusEntity>> =
        dbQuery.selectAll(::mapApplicationStatus)
            .asFlow()
            .mapToList(Dispatchers.IO)

    suspend fun getAllApplicationStatusesList(): List<ApplicationStatusEntity> {
        return withContext(Dispatchers.IO) {
            dbQuery
                .selectAll(::mapApplicationStatus)
                .executeAsList()
        }
    }

    suspend fun insertNewApplicationStatus(name: String) {
        withContext(Dispatchers.IO) {
            dbQuery.insert(name)
        }
    }

    private fun mapApplicationStatus(
        id: Long,
        name: String,
        position: Long
    ) = ApplicationStatusEntity(id, name, position)
}