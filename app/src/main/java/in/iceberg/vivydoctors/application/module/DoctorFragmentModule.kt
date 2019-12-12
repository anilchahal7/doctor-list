package `in`.iceberg.vivydoctors.application.module

import `in`.iceberg.vivydoctors.fragment.DoctorsListFragment
import `in`.iceberg.vivydoctors.fragment.RecentDoctorsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class DoctorFragmentModule {
    @ContributesAndroidInjector
    @FragmentScope
    abstract fun doctorsListFragment(): DoctorsListFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun recentDoctorsListFragment(): RecentDoctorsListFragment
}