package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import data.model.ApplicationStatus
import ir.masoudkarimi.data.db.JobTracker
import ir.masoudkarimi.data.db.JobTrackerQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface ApplicationStatusRepository {
    val flow: Flow<List<ApplicationStatus>>
    suspend fun insert(name: String)
}

internal class ApplicationStatusRepositoryImpl(
    private val db: JobTracker
) : ApplicationStatusRepository {
    override val flow: Flow<List<ApplicationStatus>>
        get() = db.jobTrackerQueries.selectAll(::mapApplicationStatus)
            .asFlow()
            .mapToList(Dispatchers.IO)

    override suspend fun insert(name: String) {
        withContext(Dispatchers.IO) {
            db.jobTrackerQueries.insert(name)
        }
    }

    private fun mapApplicationStatus(
        id: Long,
        name: String,
        position: Long
    ) = ApplicationStatus(id, name, position)
}
