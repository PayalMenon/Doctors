package com.example.doctors.dagger.module

import android.app.Application
import com.example.doctors.DoctorsApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
               ViewModelModule::class,
               ActivityBindingModule::class,
               NetworkModule::class]
)
interface ApplicationComponent : AndroidInjector<DoctorsApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: Application)
}