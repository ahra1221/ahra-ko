package org.sopt.sample.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.sample.remote.service.MusicAddService
import org.sopt.sample.remote.service.MusicShowService
import retrofit2.Retrofit

object MusicApiFactory{
    private val client by lazy{
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://3.34.53.11:8080/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object MusicServicePool{
    val musicAddService = MusicApiFactory.create<MusicAddService>()
    val musicShowService = MusicApiFactory.create<MusicShowService>()
}