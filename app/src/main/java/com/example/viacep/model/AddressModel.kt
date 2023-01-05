package com.example.viacep.model

import com.google.gson.annotations.SerializedName

class AddressModel(
    @SerializedName("logradouro")
    val location: String? = null,
    @SerializedName("bairro")
    val neighborhood: String? = null,
    @SerializedName("localidade")
    val city: String? = null,
    val uf: String? = null,
)