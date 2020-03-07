package com.example.doctors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.doctors.R
import com.example.doctors.model.Doctor
import com.example.doctors.viewmodel.SearchViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.doctor_list.view.*
import javax.inject.Inject

class DoctorListFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(
            SearchViewModel::class.java
        )
        val view = inflater.inflate(R.layout.doctor_list, container, false)
        setAdapter(view)
        return view
    }

    fun setAdapter(view : View) {
        val adapter = DoctorsListAdapter() {doctor: Doctor ->  onListItemClicked(doctor)}
        view.doctor_list.adapter = adapter
        adapter.submitList(searchViewModel.getDoctorList())
    }

    fun onListItemClicked( doctor: Doctor) {
        searchViewModel.onDoctorSelected(doctor)
    }
}