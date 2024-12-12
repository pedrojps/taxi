package com.example.car_request2.network

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {


    private val CONNECT_TIMEOUT_IN_SECONDS = 30L

    private val WRITE_TIMEOUT_IN_SECONDS = 50L

    private val READ_TIMEOUT_IN_SECONDS = 40L

    private val httpClient: OkHttpClient.Builder? = OkHttpClient.Builder()
        .connectTimeout(CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })

    //Retrofit2 OkHttp Url
    fun <T> createService(serviceClass: Class<T>?, url: HttpUrl?): T {

        return Retrofit.Builder()
            .client(httpClient?.build())
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(serviceClass)
    }

}