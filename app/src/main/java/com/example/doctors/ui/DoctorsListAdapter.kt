package com.example.doctors.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.doctors.R
import com.example.doctors.model.Doctor
import kotlinx.android.synthetic.main.doctor_item.view.*

class DoctorsListAdapter (val onClickListener: (Doctor) -> Unit) : PagedListAdapter<Doctor, DoctorsViewHolder>(DoctorDiffUtil()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorsViewHolder {
        return DoctorsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.doctor_item, parent, false))
    }

    override fun onBindViewHolder(holder: DoctorsViewHolder, position: Int) {
        getItem(position)?.let { doctor ->
            holder.setListItems(doctor, onClickListener)
        }
        return
    }
}

class DoctorsViewHolder (val view: View): RecyclerView.ViewHolder(view) {

    fun setListItems(doctor: Doctor, onClickListener: (Doctor) -> Unit) {
        view.doctor_name.text = doctor.profile.firstName.plus(" ").plus(doctor.profile.lastName).plus(" , ").plus(doctor.profile.title)
        view.doctor_speciality.text = doctor.specialties.joinToString { it.name }
        Glide.with(view.context).load(doctor.profile.imageUrl).into(view.doctor_image)
        view.doctor_image.visibility = View.VISIBLE
        view.doctor_item_parent.setOnClickListener { onClickListener(doctor) }
    }
}