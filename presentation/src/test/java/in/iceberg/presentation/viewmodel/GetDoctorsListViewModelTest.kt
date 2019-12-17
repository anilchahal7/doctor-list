package `in`.iceberg.presentation.viewmodel

import `in`.iceberg.domain.response.DoctorsListResponse
import `in`.iceberg.domain.usecase.GetDoctorsList
import `in`.iceberg.presentation.datafactory.DataFactory
import `in`.iceberg.presentation.state.ResourceState
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableSingleObserver
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetDoctorsListViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor
    private val captor = argumentCaptor<DisposableSingleObserver<DoctorsListResponse>>()

    private val getDoctorsList = mock<GetDoctorsList>()
    private val getDoctorsListViewModel = GetDoctorsListViewModel(getDoctorsList)

    @Test
    fun testGetDoctorsListResponseUseCase() {
        val lastKey = DataFactory.getRandomString()
        getDoctorsListViewModel.getSearchResponse(lastKey)
        verify(getDoctorsList, times(1)).execute(any(),
            eq(GetDoctorsList.Params(lastKey)))
    }

    @Test
    fun testGetDoctorsListResponseReturnsData() {
        val lastKey = DataFactory.getRandomString()
        val doctorsListResponse = DataFactory.getRandomDoctorListResponse()
        getDoctorsListViewModel.getSearchResponse(lastKey)
        verify(getDoctorsList, times(1)).execute(captor.capture(),
            eq(GetDoctorsList.Params(lastKey)))
        captor.firstValue.onSuccess(doctorsListResponse)
        assertEquals(doctorsListResponse, getDoctorsListViewModel.observeGetSearchResponse().value?.data)
        assertEquals(ResourceState.SUCCESS, getDoctorsListViewModel.observeGetSearchResponse().value?.status)
    }

    @Test
    fun testGetDoctorsListReturnsError() {
        val message = DataFactory.getRandomString()
        val lastKey = DataFactory.getRandomString()
        getDoctorsListViewModel.getSearchResponse(lastKey)
        verify(getDoctorsList).execute(captor.capture(), eq(GetDoctorsList.Params(lastKey)))
        captor.firstValue.onError(RuntimeException(message))
        assertEquals(ResourceState.ERROR, getDoctorsListViewModel.observeGetSearchResponse().value?.status)
        assertEquals(message, getDoctorsListViewModel.observeGetSearchResponse().value?.error)
    }

    @Test
    fun getDoctorsListResponseCompletesTest() {
        val lastKey = DataFactory.getRandomString()
        getDoctorsListViewModel.getSearchResponse(lastKey)
        verify(getDoctorsList).execute(
            Mockito.any<DisposableSingleObserver<DoctorsListResponse>>(),
            eq(GetDoctorsList.Params.getParams(lastKey)))
    }
}