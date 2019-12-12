package `in`.iceberg.data.store

import `in`.iceberg.data.repository.DoctorsDataStore
import `in`.iceberg.data.repository.DoctorsRemote
import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single
import javax.inject.Inject

class DoctorsRemoteDataStore @Inject constructor(private val doctorsRemote: DoctorsRemote):
        DoctorsDataStore {
    override fun getDoctorsList(lastKey: String): Single<DoctorsListResponse> {
        return doctorsRemote.getDoctorsList(lastKey)
    }
}