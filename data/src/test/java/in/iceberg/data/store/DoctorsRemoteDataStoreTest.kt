package `in`.iceberg.data.store

import `in`.iceberg.data.datafactory.DataFactory
import `in`.iceberg.data.repository.DoctorsRemote
import `in`.iceberg.domain.response.DoctorsListResponse
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class DoctorsRemoteDataStoreTest {

    @Mock
    private lateinit var doctorsRemote: DoctorsRemote

    private lateinit var doctorsRemoteDataStore: DoctorsRemoteDataStore

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        doctorsRemoteDataStore = DoctorsRemoteDataStore(doctorsRemote)
    }

    private fun stubGetDoctorsListResponse(single: Single<DoctorsListResponse>, lastKey: String) {
        Mockito.`when`(doctorsRemote.getDoctorsList(lastKey)).thenReturn(single)
    }

    @Test
    fun getDoctorsListResponseCompletesTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        val testObserver = doctorsRemoteDataStore.getDoctorsList(lastKey).test()
        testObserver.assertComplete()
    }

    @Test
    fun getDoctorListReturnsDataTest() {
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        val lastKey = DataFactory.getRandomString()
        stubGetDoctorsListResponse(Single.just(doctorsListResponse), lastKey)
        val testObserver = doctorsRemoteDataStore.getDoctorsList(lastKey).test()
        testObserver.assertValue(doctorsListResponse)
    }
}