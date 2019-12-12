package `in`.iceberg.data.repository

import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single

interface DoctorsRemote {
    fun getDoctorsList(lastKey: String): Single<DoctorsListResponse>
}