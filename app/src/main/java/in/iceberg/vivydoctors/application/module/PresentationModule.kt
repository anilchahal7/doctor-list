package `in`.iceberg.vivydoctors.application.module

import `in`.iceberg.presentation.viewmodel.GetDoctorsListViewModel
import `in`.iceberg.vivydoctors.dependencies.ViewModelFactory
import `in`.iceberg.vivydoctors.dependencies.ViewModelKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule  {
    @Binds
    @IntoMap
    @ViewModelKey(GetDoctorsListViewModel::class)
    abstract fun bindsGetDoctorsListViewModel(getDoctorsListViewModel: GetDoctorsListViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}