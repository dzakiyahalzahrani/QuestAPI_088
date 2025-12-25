package com.example.questapi_088.repositori

// Nama file ini kemungkinan: ContainerApp.kt atau AppContainer.kt

import android.app.Application
import com.example.questapi_088.apiservice.ServiceApiSiswa
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit // <-- PASTIKAN IMPORT INI ADA

interface ContainerApp{
    val repositoryDataSiswa: RepositoryDataSiswa
}

class DefaultContainerApp : ContainerApp{
    // Pastikan URL ini sudah benar sesuai nama folder di htdocs
    private val baseurl = "http://10.0.2.2/umyTI/"

    // Interceptor untuk melihat log request dan response di Logcat
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // --- INI BAGIAN PERBAIKANNYA ---
    // Membuat OkHttpClient dengan batas waktu tunggu yang lebih lama
    private val klien = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS) // Waktu tunggu koneksi 30 detik
        .readTimeout(30, TimeUnit.SECONDS)    // Waktu tunggu membaca data 30 detik
        .build()
    // --- AKHIR DARI PERBAIKAN ---

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseurl)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType())
        )
        .client(klien) // Menggunakan klien OkHttp yang sudah dikonfigurasi
        .build()

    private val retrofitService: ServiceApiSiswa by lazy {
        retrofit.create(ServiceApiSiswa::class.java)
    }

    override val repositoryDataSiswa: RepositoryDataSiswa by lazy {
        JaringanRepositoryDataSiswa(retrofitService)
    }
}

class AplikasiDataSiswa : Application() {
    lateinit var containerApp: ContainerApp
    override fun onCreate() {
        super.onCreate()
        containerApp = DefaultContainerApp()
    }
}
