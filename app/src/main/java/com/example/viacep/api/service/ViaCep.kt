package com.example.viacep.api.service

import com.example.viacep.model.AddressModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCep {
    @GET("ws/{cep}/json/")
    suspend fun addressByCep(@Path("cep") cep: String): AddressModel?
}