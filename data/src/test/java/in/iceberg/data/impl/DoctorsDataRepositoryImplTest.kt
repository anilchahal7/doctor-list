package `in`.iceberg.data.impl

import `in`.iceberg.data.datafactory.DataFactory
import `in`.iceberg.data.store.DoctorsDataStoreFactory
import `in`.iceberg.data.store.DoctorsRemoteDataStore
import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class DoctorsDataRepositoryImplTest {

    private val dataStoreFactory = Mockito.mock(DoctorsDataStoreFactory::class.java)
    private val remoteDataStore = Mockito.mock(DoctorsRemoteDataStore::class.java)

    private val doctorsDataRepository = DoctorsDataRepositoryImpl(dataStoreFactory)

    @Before
    fun setup() {
        Mockito.`when`(dataStoreFactory.doctorsRemoteDataStore).thenReturn(remoteDataStore)
    }

    private fun stubGetDoctorsListResponse(single: Single<DoctorsListResponse>, lastKey: String) {
        Mockito.`when`(remoteDataStore.getDoctorsList(lastKey)).thenReturn(single)
    }

    @Test
    fun getDoctorsListResponseCompletesTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        val testObserver = doctorsDataRepository.getDoctorsList(lastKey).test()
        testObserver.assertComplete()
    }

    @Test
    fun getDoctorsDataResponseReturnsTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        val testObserver = doctorsDataRepository.getDoctorsList(lastKey).test()
        testObserver.assertValue(doctorsListResponse)
    }
}