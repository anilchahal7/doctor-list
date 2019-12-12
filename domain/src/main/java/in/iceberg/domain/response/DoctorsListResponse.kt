package `in`.iceberg.domain.response

import `in`.iceberg.domain.model.Doctor

data class DoctorsListResponse(
    val doctors: MutableList<Doctor>,
    val lastKey: String
)