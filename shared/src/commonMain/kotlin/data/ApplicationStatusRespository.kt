package data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import data.model.ApplicationStatus
import data.model.Job
import ir.masoudkarimi.data.db.JobTracker

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime

interface ApplicationStatusRepository {
    val flow: Flow<List<ApplicationStatus>>
    suspend fun insert(name: String)

    suspend fun insertJob(job: Job)

    suspend fun deleteJob(jobId: Long)
}

internal class ApplicationStatusRepositoryImpl(
    private val db: JobTracker
) : ApplicationStatusRepository {
    override val flow: Flow<List<ApplicationStatus>>
        get() = db.jobTrackerQueries.selectAllApplicationStatusesWithJobs()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapLatest { applicationStatusWithJobs ->
                applicationStatusWithJobs.groupBy { it.statusId }
                    .mapValues { entry ->
                        val status = entry.value.first()
                        val jobs = entry.value.filter {
                            it.jobId != null
                        }.map { job ->
                            Job(
                                id = job.jobId!!,
                                companyName = job.companyName.orEmpty(),
                                jobTitle = job.jobTitle.orEmpty(),
                                salary = job.salary.orEmpty(),
                                location = job.location.orEmpty(),
                                link = job.link.orEmpty(),
                                statusId = job.applicationStatusId!!,
                                createdAt = LocalDateTime.parse(job.createdAt!!)
                            )
                        }

                        ApplicationStatus(
                            statusId = status.statusId,
                            statusName = status.statusName,
                            statusPosition = status.statusPosition,
                            jobCount = status.statusJobCount.toInt(),
                            jobs = jobs
                        )
                    }.values.toList()

            }.flowOn(Dispatchers.IO)

    override suspend fun insert(name: String) {
        withContext(Dispatchers.IO) {
            db.jobTrackerQueries.insertApplicationStatus(name)
        }
    }


    override suspend fun insertJob(job: Job) {
        withContext(Dispatchers.IO) {
            db.jobTrackerQueries.insertJob(
                companyName = job.companyName,
                jobTitle = job.jobTitle,
                salary = job.salary,
                location = job.location,
                link = job.link,
                applicationStatusId = job.statusId,
                createdAt = job.createdAt.toString(),
            )
        }
    }

    override suspend fun deleteJob(jobId: Long) {
        withContext(Dispatchers.IO) {
            db.jobTrackerQueries.deleteJob(jobId)
        }
    }
}
