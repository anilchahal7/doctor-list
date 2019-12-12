package `in`.iceberg.vivydoctors.application

import `in`.iceberg.vivydoctors.application.module.*
import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        UIModule::class,
        PresentationModule::class,
        DataModule::class,
        RemoteModule::class
    ]
)
interface ApplicationComponent {
    fun inject(app: DoctorsApplication)
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}