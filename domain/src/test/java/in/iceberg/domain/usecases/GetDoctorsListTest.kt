package `in`.iceberg.domain.usecases

import `in`.iceberg.domain.datafactory.DataFactory
import `in`.iceberg.domain.executor.PostExecutionThread
import `in`.iceberg.domain.repository.DoctorsRepository
import `in`.iceberg.domain.response.DoctorsListResponse
import `in`.iceberg.domain.usecase.GetDoctorsList
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetDoctorsListTest {
    @Mock
    private lateinit var postExecutionThread: PostExecutionThread

    @Mock
    private lateinit var doctorsRepository: DoctorsRepository

    private lateinit var getDoctorsList: GetDoctorsList

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getDoctorsList = GetDoctorsList(doctorsRepository, postExecutionThread)
    }

    private fun stubGetDoctorsListResponse(single: Single<DoctorsListResponse>, lastKey: String) {
        Mockito.`when`(doctorsRepository.getDoctorsList(lastKey)).thenReturn(single)
    }

    @Test
    fun getDoctorListCompletesTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        val testObserver = getDoctorsList.buildUseCaseObservable(
            GetDoctorsList.Params.getParams(lastKey)).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getDoctorListThrowsErrorIfParamsIsNullTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        getDoctorsList.buildUseCaseObservable(null).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getDoctorsListThrowsErrorIfObserverIsNullTest() {
        getDoctorsList = GetDoctorsList(doctorsRepository, postExecutionThread)
        getDoctorsList.execute(null)
    }
}