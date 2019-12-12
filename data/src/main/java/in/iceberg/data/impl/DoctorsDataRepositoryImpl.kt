package `in`.iceberg.data.impl

import `in`.iceberg.data.store.DoctorsDataStoreFactory
import `in`.iceberg.domain.repository.DoctorsRepository
import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single
import javax.inject.Inject

class DoctorsDataRepositoryImpl @Inject constructor(
        private val doctorsDataStoreFactory: DoctorsDataStoreFactory
    ): DoctorsRepository {

    override fun getDoctorsList(lastKey: String): Single<DoctorsListResponse> {
        return doctorsDataStoreFactory.doctorsRemoteDataStore.getDoctorsList(lastKey)
    }
}