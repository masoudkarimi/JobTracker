package data.model

data class ApplicationStatus(
    val statusId: Long,
    val statusName: String,
    val statusPosition: Long,
    val jobCount: Int,
    val jobs: List<Job>
)
