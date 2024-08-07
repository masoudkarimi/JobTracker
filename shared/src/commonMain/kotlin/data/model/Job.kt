package data.model

import kotlinx.datetime.LocalDateTime

data class Job(
    val id: Long,
    val companyName: String,
    val jobTitle: String,
    val salary: String,
    val location: String,
    val link: String,
    val statusId: Long,
    val createdAt: LocalDateTime
)