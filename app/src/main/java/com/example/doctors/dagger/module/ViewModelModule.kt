package com.example.doctors.dagger.module

import androidx.lifecycle.ViewModel
import com.example.doctors.dagger.ViewModelKey
import com.example.doctors.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel : SearchViewModel) : ViewModel
}