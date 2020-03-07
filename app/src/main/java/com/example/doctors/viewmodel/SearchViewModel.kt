package com.example.doctors.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.doctors.api.DoctorDataFactory
import com.example.doctors.api.DoctorDataSource
import com.example.doctors.model.Doctor
import com.example.doctors.util.Event
import javax.inject.Inject

class SearchViewModel  @Inject constructor(application: Application,
                                           val doctorDataFactory: DoctorDataFactory
) : AndroidViewModel(application) {

    //Live Data
    private val _showLoading = MutableLiveData<Event<Unit>>()
    val showLoading: LiveData<Event<Unit>>
        get() = _showLoading

    private val _hideLoading = MutableLiveData<Event<Unit>>()
    val hideLoading: LiveData<Event<Unit>>
        get() = _hideLoading

    private val _hideText = MutableLiveData<Event<Unit>>()
    val hideText: LiveData<Event<Unit>>
        get() = _hideText

    private val _showDoctorListFragment = MutableLiveData<Event<Unit>>()
    val showDoctorListFragment: LiveData<Event<Unit>>
        get() = _showDoctorListFragment

    private val _showDoctorDetailFragment = MutableLiveData<Event<Doctor>>()
    val showDoctorDetailFragment: LiveData<Event<Doctor>>
        get() = _showDoctorDetailFragment

    var doctorLiveData: LiveData<PagedList<Doctor>> ? =null

    var networkState : LiveData<Event<String>> = Transformations.switchMap<DoctorDataSource, Event<String>>(doctorDataFactory.dataSourceLiveData, DoctorDataSource::networkState)

    fun onSearchSelected(queryString: String) {
        _showLoading.value = Event(Unit)
        _hideText.value = Event(Unit)

        doctorDataFactory.setQueryString(queryString)
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10).build()
        doctorLiveData = LivePagedListBuilder<Int, Doctor>(doctorDataFactory, pagedListConfig).build()
    }

    fun onDoctorListReceived(list : PagedList<Doctor>) {
        _hideLoading.value = Event(Unit)
        _showDoctorListFragment.value = Event(Unit)
    }

    fun getDoctorList() : PagedList<Doctor>? {
        return doctorLiveData?.value
    }

    fun onDoctorSelected(doctor: Doctor) {
        _showDoctorDetailFragment.value = Event(doctor)
    }
}