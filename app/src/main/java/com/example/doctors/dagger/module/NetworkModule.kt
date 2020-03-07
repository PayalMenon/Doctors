package com.example.doctors.dagger.module

import com.example.doctors.BuildConfig
import com.example.doctors.api.ApiService
import com.example.doctors.api.DoctorDataFactory
import com.example.doctors.api.DoctorDataSource
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val DOCTOR_BASE_URL = "https://api.betterdoctor.com/"
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val url: HttpUrl = original.url().newBuilder()
                .addQueryParameter("user_key", BuildConfig.DOCTOR_API_KEY)
                .build()

            val request = original.newBuilder().url(url).build()
            chain.proceed(request)
        }
        val newBuilder = httpBuilder.build().newBuilder().addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)
        return newBuilder.build()
    }

    @Singleton
    @Provides
    fun providesRetrofitService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(DOCTOR_BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDoctorDataFactory(doctorDataSource: DoctorDataSource) : DoctorDataFactory {
        return DoctorDataFactory(doctorDataSource)
    }

    @Provides
    @Singleton
    fun provideDoctorDataSource(apiService: ApiService) : DoctorDataSource {
        return DoctorDataSource(apiService)
    }
}