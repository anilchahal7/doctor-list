package `in`.iceberg.vivydoctors.application.module

import `in`.iceberg.data.repository.DoctorsRemote
import `in`.iceberg.remote.impl.DoctorsRemoteImpl
import `in`.iceberg.remote.service.DoctorsApiServiceFactory
import `in`.iceberg.remote.service.DoctorsListService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RemoteModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun providesDoctorsListService() : DoctorsListService {
            return DoctorsApiServiceFactory.geDoctorsListApiService()
        }
    }
    @Binds
    abstract fun bindsDoctorsRemote(doctorsRemoteImpl: DoctorsRemoteImpl): DoctorsRemote
}