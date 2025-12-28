package com.example.pizzaapp3387.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Ganti IP dan XXXX sesuai konfigurasi komputer kamu
    const val BASE_URL = "http://10.0.2.2/rest_api3387/index.php/"

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}