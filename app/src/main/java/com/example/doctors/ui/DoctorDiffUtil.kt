package com.example.doctors.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.doctors.model.Doctor

class DoctorDiffUtil : DiffUtil.ItemCallback<Doctor>() {
    override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
        return oldItem.id == newItem.id && oldItem.npi == newItem.npi
    }

}