package com.example.restaurantreview

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig { // class ini untuk membuat dan mengkonfigurasi Retrofit

    /*
    Kelas ini akan membuat kode Anda menjadi lebih efektif karena Anda tidak perlu membuat konfigurasi
    Retrofit baru setiap kali membutuhkannya, tetapi cukup memanggil fungsi yang ada di dalam class ini saja
    Dengan membuat class ini, kita tidak perlu menyiapkan Retrofit pada setiap class yang akan menggunakan Retrofit.
    kita hanya cukup memanggil ApiConfig.getApiService(). Cukup simpel daripada Anda harus menulis berulang-ulang
     */

    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restaurant-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}