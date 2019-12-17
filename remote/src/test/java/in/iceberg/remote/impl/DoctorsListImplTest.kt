package `in`.iceberg.remote.impl

import `in`.iceberg.domain.response.DoctorsListResponse
import `in`.iceberg.remote.datafactory.DataFactory
import `in`.iceberg.remote.service.DoctorsApiServiceFactory
import `in`.iceberg.remote.service.DoctorsListService
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class DoctorsListImplTest {

    private val serviceFactory = Mockito.mock(DoctorsApiServiceFactory::class.java)
    private val doctorsListService = Mockito.mock(DoctorsListService::class.java)
    private val doctorsListRemoteImpl = DoctorsRemoteImpl(doctorsListService)

    @Before
    fun setup() {
        stubGetService()
    }

    private fun stubGetService() {
        Mockito.`when`(serviceFactory.geDoctorsListApiService()).thenReturn(doctorsListService)
    }

    private fun stubGetDoctorsListResponse(single: Single<DoctorsListResponse>, lastKey: String) {
        Mockito.`when`(doctorsListService.getListOfDoctors(lastKey)).thenReturn(single)
    }

    @Test
    fun getDoctorsListResponseCompletesTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        val testObserver = doctorsListRemoteImpl.getDoctorsList(lastKey).test()
        testObserver.assertComplete()
    }

    @Test
    fun getDoctorsListResponseApiCalled() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        doctorsListRemoteImpl.getDoctorsList(lastKey).test()
        verify(doctorsListService).getListOfDoctors(lastKey)
    }

    @Test
    fun getDoctorsDataReturnsDataTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        val testObserver = doctorsListRemoteImpl.getDoctorsList(lastKey).test()
        testObserver.assertValue(doctorsListResponse)
    }
}