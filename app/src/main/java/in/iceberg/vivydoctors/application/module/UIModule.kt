package `in`.iceberg.vivydoctors.application.module

import `in`.iceberg.domain.executor.PostExecutionThread
import `in`.iceberg.vivydoctors.activity.MainActivity
import `in`.iceberg.vivydoctors.application.UIThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {
    @Binds
    abstract fun bindsPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector(modules = [DoctorFragmentModule::class])
    abstract fun contributesTaxiMainActivity(): MainActivity
}