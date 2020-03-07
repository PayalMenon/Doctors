package com.example.doctors.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.doctors.model.Doctor
import javax.inject.Inject

class DoctorDataFactory @Inject constructor(val doctorDataSource: DoctorDataSource) :
    DataSource.Factory<Int, Doctor>() {

    val dataSourceLiveData = MutableLiveData<DoctorDataSource>()

    fun setQueryString(query: String) {
        doctorDataSource.setQuery(query)
    }

    override fun create(): DataSource<Int, Doctor> {
        dataSourceLiveData.postValue(doctorDataSource)
        return doctorDataSource
    }
}