package data.entity

internal data class ApplicationStatusEntityWithJobs(
    val statusId: Long,
    val statusName: String,
    val statusPosition: Int,
    val jobsCount: Int,
    val jobId: Long?,
    val companyName: String?,
    val jobTitle: String?,
    val salary: String?,
    val location: String?,
    val link: String?,
    val applicationStatusId: Long?,
    val createdAt: String?  // ISO-8601 format => 2020-07-10 15:00:00.000
)