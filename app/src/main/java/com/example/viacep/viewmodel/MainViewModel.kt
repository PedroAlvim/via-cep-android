package com.example.viacep.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viacep.model.AddressModel
import com.example.viacep.repository.AddressRepository
import com.example.viacep.ui.event.MessageEvent
import com.example.viacep.ui.event.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    companion object{
        const val TAG = "MainViewModel"
    }

    private val addressRepository: AddressRepository = AddressRepository()
    private var addressModel: AddressModel? = null

    private var _location = MutableLiveData<String>()
    val location: LiveData<String> get() = _location

    private var _neighborhood = MutableLiveData<String>()
    val neighborhood: LiveData<String> get() = _neighborhood

    private var _city = MutableLiveData<String>()
    val city: LiveData<String> get() = _city

    private var _uf = MutableLiveData<String>()
    val uf: LiveData<String> get() = _uf

    private var _eventMessage = SingleLiveEvent<MessageEvent>()
    val eventMessage: LiveData<MessageEvent> get() = _eventMessage

    fun getAddress(cep: String?) {
        if (cep.isNullOrEmpty()) {
            _eventMessage.postValue(MessageEvent.ShowEmptyCep)
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _eventMessage.postValue(MessageEvent.ShowLoadingCep)

            try {
                addressModel = addressRepository.getAddressByCep(cep)
                addressModel?.let { address ->
                    _location.postValue(address.location.toString())
                    _neighborhood.postValue(address.neighborhood.toString())
                    _city.postValue(address.city.toString())
                    _uf.postValue(address.uf.toString())

                    _eventMessage.postValue(MessageEvent.ShowFindAddress)
                } ?: _eventMessage.postValue(MessageEvent.ShowNotFindAddress)

            } catch (e: Exception) {
                Log.e(TAG, "Failed to load info")
                _eventMessage.postValue(MessageEvent.ShowErrorFindAddress)
            }
        }
    }
}