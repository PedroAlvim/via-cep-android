package com.example.viacep.api

import com.example.viacep.api.service.ViaCep
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInit {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val addressByCepService: ViaCep = retrofit.create(ViaCep::class.java)
}