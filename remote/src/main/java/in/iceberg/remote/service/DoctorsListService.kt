package `in`.iceberg.remote.service

import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single
import retrofit2.http.*

interface DoctorsListService {
    @GET
    fun getListOfDoctors(@Url pathUrl: String): Single<DoctorsListResponse>
}