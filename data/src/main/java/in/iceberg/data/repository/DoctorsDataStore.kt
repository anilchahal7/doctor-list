package `in`.iceberg.data.repository

import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single

interface DoctorsDataStore {
    fun getDoctorsList(lastKey: String): Single<DoctorsListResponse>
}