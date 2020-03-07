package com.example.doctors.api

import com.example.doctors.model.Doctors
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService  {

    @GET("2016-03-01/doctors")
    fun getDoctorList(
        @Query("skip") page: Int,
        @Query("query") query: String
    ): Call<Doctors>
}