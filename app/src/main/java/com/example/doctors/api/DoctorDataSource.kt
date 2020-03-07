package com.example.doctors.api

import android.util.EventLog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.doctors.model.Doctor
import com.example.doctors.model.Doctors
import com.example.doctors.util.Event
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class DoctorDataSource @Inject constructor(val apiService: ApiService) : PageKeyedDataSource<Int, Doctor>() {

    var queryString : String? = null
    var networkState : MutableLiveData<Event<String>> = MutableLiveData()

    fun setQuery(query : String) {
        queryString = query;
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Doctor>
    ) {
        try {
            queryString?.let { query ->
                val call: Call<Doctors> = apiService.getDoctorList(0, query)
                val result: Response<Doctors> = call.execute()
                if (result.isSuccessful) {
                    result.body()?.data?.let { doctors ->
                        callback.onResult(doctors, -1, 1)
                    }
                } else {
                    Log.d("Doctors App", "Oops something went wrong. Please try again - loadInitial")
                    networkState.postValue(Event("Oops something went wrong. Please try again"))
                }
            }

        } catch (exception: IOException) {
            Log.d("Doctors App", "Oops something went wrong with network. Please check your connectivity and try again - loadInitial")
            networkState.postValue(Event("Oops something went wrong with network. Please check your connectivity and try again"))
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Doctor>) {
        try {
            queryString?.let { query ->
                val call: Call<Doctors> = apiService.getDoctorList(params.key, query)
                val result: Response<Doctors> = call.execute()
                if (result.isSuccessful) {
                    result.body()?.data?.let { doctors ->
                        callback.onResult(doctors, params.key  + 1 )
                    }
                } else {
                    Log.d("Doctors App", "Oops something went wrong. Please try again - loadAfter")
                }
            }

        } catch (exception: IOException) {
            Log.d("Doctors App", "Oops something went wrong with network. Please check your connectivity and try again - loadAfter")
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Doctor>) {
        try {
            queryString?.let { query ->
                val call: Call<Doctors> = apiService.getDoctorList(params.key, query)
                val result: Response<Doctors> = call.execute()
                if (result.isSuccessful) {
                    result.body()?.data?.let { doctors ->
                        callback.onResult(doctors, if (params.key > 1) params.key - 1 else 0 )
                    }
                } else {
                    Log.d("Doctors App", "Oops something went wrong. Please try again - loadBefore")
                }
            }

        } catch (exception: IOException) {
            Log.d("Doctors App", "Oops something went wrong with network. Please check your connectivity and try again - loadBefore")
        }
    }
}
