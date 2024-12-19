package com.example.myapplication.ui.backendset

// RetrofitClient.kt
import com.example.test.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"  // API의 기본 URL

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃 (30초)
        .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃 (30초)
        .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 타임아웃 (30초)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)  // OkHttpClient 적용
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
