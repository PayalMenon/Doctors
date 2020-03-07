package com.example.doctors.dagger.module

import com.example.doctors.dagger.ViewModelBuilder
import com.example.doctors.ui.DoctorDetailFragment
import com.example.doctors.ui.DoctorListFragment
import com.example.doctors.ui.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun startActivity(): SearchActivity

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun doctorListFragment(): DoctorListFragment

    @ContributesAndroidInjector(modules = [ViewModelBuilder::class])
    abstract fun doctorDetailFragment(): DoctorDetailFragment

}