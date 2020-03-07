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
import kotlinx.android.synthetic.main.doctor_details.view.*
import javax.inject.Inject

class DoctorDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    companion object {
        private const val DOCTOR = "doctor"
        private const val LANDLINE = "landline"

        fun newInstance(doctor: Doctor) : DoctorDetailFragment {
            val bundle = Bundle()
            bundle.putParcelable(DOCTOR, doctor)
            val fragment = DoctorDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        searchViewModel = ViewModelProviders.of(requireActivity(), viewModelFactory).get(
            SearchViewModel::class.java
        )
        val view = inflater.inflate(R.layout.doctor_details, container, false)
        val doctor = arguments?.getParcelable<Doctor>(DOCTOR)
        doctor?.let {
            setupViews(view, it)
        }
        return view
    }

    fun setupViews(view: View, doctor: Doctor) {
        view.doctor_name.text = doctor.profile.firstName.plus(" ")
            .plus(doctor.profile.lastName).plus(" , ")
            .plus(doctor.profile.title)

        view.doctor_speciality.text = doctor.specialties.joinToString { it.name }
        val practices = doctor.practices
        if(!practices.isNullOrEmpty()){
            view.doctor_address_line1.text = practices[0].address.street
            view.doctor_address_line2.text = practices[0].address.city.plus(" , ")
                .plus(practices[0].address.state).plus(" , ")
                .plus(practices[0].address.zip)
            view.doctor_address_line1.visibility = View.VISIBLE
            view.doctor_address_line2.visibility = View.VISIBLE
            view.doctor_address_text.visibility = View.VISIBLE

            val phones = practices[0].phones
            if(!phones.isNullOrEmpty()) {
                view.doctor_phone.text = phones.filter { it.type == LANDLINE }[0].type
                    .plus(" : ").plus(phones[0].number)
                view.doctor_phone.visibility = View.VISIBLE
                view.doctor_phone_text.visibility = View.VISIBLE
            }
        }
        view.doctor_bio.text = doctor.profile.bio
    }
}