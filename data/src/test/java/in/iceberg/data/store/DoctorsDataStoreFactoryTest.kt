package `in`.iceberg.data.store

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class DoctorsDataStoreFactoryTest {

    @Mock
    private lateinit var doctorsRemoteDataStore: DoctorsRemoteDataStore

    private lateinit var doctorsDataStoreFactory: DoctorsDataStoreFactory

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        doctorsDataStoreFactory = DoctorsDataStoreFactory(doctorsRemoteDataStore)
    }

    @Test
    fun getsRemoteDataStoreCorrectlyTest() {
        Assert.assertEquals(doctorsRemoteDataStore, doctorsDataStoreFactory.doctorsRemoteDataStore)
    }
}