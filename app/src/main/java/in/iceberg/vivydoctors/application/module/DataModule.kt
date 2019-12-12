package `in`.iceberg.vivydoctors.application.module

import `in`.iceberg.data.impl.DoctorsDataRepositoryImpl
import `in`.iceberg.domain.repository.DoctorsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindsDoctorsRepository(doctorsDataRepositoryImpl: DoctorsDataRepositoryImpl):
            DoctorsRepository
}