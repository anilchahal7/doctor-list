package `in`.iceberg.domain.repository

import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single

interface DoctorsRepository {
    fun getDoctorsList(lastKey: String): Single<DoctorsListResponse>
}