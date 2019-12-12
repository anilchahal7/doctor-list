package `in`.iceberg.domain.usecase

import `in`.iceberg.domain.executor.PostExecutionThread
import `in`.iceberg.domain.repository.DoctorsRepository
import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single
import javax.inject.Inject

class GetDoctorsList @Inject
    constructor(private val doctorsRepository: DoctorsRepository,
                postExecutionThread: PostExecutionThread
    ): SingleUseCase<DoctorsListResponse, GetDoctorsList.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Single<DoctorsListResponse> {
        params?.let {
            return doctorsRepository.getDoctorsList(it.lastKey)
        }
        throw IllegalArgumentException()
    }

    data class Params constructor(val lastKey: String) {
        companion object {
            fun getParams(lastKey: String) =
                Params(lastKey)
        }
    }
}