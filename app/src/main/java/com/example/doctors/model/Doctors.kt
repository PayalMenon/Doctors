package com.example.doctors.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Doctors(
    val data: List<Doctor>
)

@Parcelize
data class Doctor(
    val profile: Profile,
    val practices: List<Practice>?,
    val specialties: List<Speciality>,
    @SerializedName("uid")
    val id : String,
    val npi : String
) : Parcelable

@Parcelize
data class Profile(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val title: String,
    @SerializedName("image_url")
    val imageUrl: String,
    val gender: String,
    val bio: String
) : Parcelable

@Parcelize
data class Address(
    val city: String,
    val state: String,
    val street: String,
    val street2: String,
    val zip: String
) : Parcelable

@Parcelize
data class Speciality(
    val name: String
) : Parcelable

@Parcelize
data class Phone(
    val number: String,
    val type: String
) : Parcelable

@Parcelize
data class Practice(
    @SerializedName("visit_address")
    val address: Address,
    val phones: List<Phone>
) : Parcelable