package com.example.viacep.repository

import com.example.viacep.api.RetrofitInit
import com.example.viacep.model.AddressModel

class AddressRepository {
    suspend fun getAddressByCep(cep: String): AddressModel? {
        return RetrofitInit().addressByCepService.addressByCep(cep)
    }
}