package `in`.iceberg.remote.impl

import `in`.iceberg.data.repository.DoctorsRemote
import `in`.iceberg.domain.response.DoctorsListResponse
import `in`.iceberg.remote.service.DoctorsListService
import io.reactivex.Single
import javax.inject.Inject

class DoctorsRemoteImpl @Inject constructor(private val doctorsListService: DoctorsListService):
        DoctorsRemote {

    override fun getDoctorsList(lastKey: String): Single<DoctorsListResponse> {
        val pathUrl: String = if (lastKey.isEmpty()) {
            "/interviews/challenges/android/doctors.json"
        } else {
            "/interviews/challenges/android/doctors-$lastKey.json"
        }
        return doctorsListService.getListOfDoctors(pathUrl)
    }
}