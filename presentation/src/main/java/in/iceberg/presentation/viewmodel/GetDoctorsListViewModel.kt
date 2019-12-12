package `in`.iceberg.presentation.viewmodel

import `in`.iceberg.domain.response.DoctorsListResponse
import `in`.iceberg.domain.usecase.GetDoctorsList
import `in`.iceberg.presentation.state.Resource
import `in`.iceberg.presentation.state.ResourceState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class GetDoctorsListViewModel @Inject constructor(
        private val getDoctorsList: GetDoctorsList): ViewModel() {

    private val getDoctorsListResponseLiveData = MutableLiveData<Resource<DoctorsListResponse>>()

    override fun onCleared() {
        getDoctorsList.disposeAll()
    }

    // Observe Get Login Token Response ...
    fun observeGetSearchResponse() : LiveData<Resource<DoctorsListResponse>> {
        return getDoctorsListResponseLiveData
    }

    // Get Search Doctor Response ...
    fun getSearchResponse(lastKey: String) {
        getDoctorsListResponseLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getDoctorsList.execute(GetSearchResponseSubscriber(), GetDoctorsList.Params.
            getParams(lastKey))
    }

    // Get Login Token Subscriber ...
    private inner class GetSearchResponseSubscriber : DisposableSingleObserver<DoctorsListResponse>() {
        override fun onSuccess(t: DoctorsListResponse) {
            getDoctorsListResponseLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }
        override fun onError(e: Throwable) {
            getDoctorsListResponseLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }
    }
}