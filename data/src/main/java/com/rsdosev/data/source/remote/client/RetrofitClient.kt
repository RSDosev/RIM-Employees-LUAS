package com.rsdosev.data.source.remote.client

import com.rsdosev.data.source.remote.LuasForecastingAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(private val baseUrl: String) {

    val retrofitClient: Retrofit
        get() = Retrofit.Builder()
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    val luasForecastingAPIService
        get() = retrofitClient.create(LuasForecastingAPI::class.java)

    val okHttpClient: OkHttpClient
        get() = OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

}