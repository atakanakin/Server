package com.atakan.mainserver.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atakan.mainserver.data.model.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ClientDataViewModel @Inject constructor() : ViewModel() {
    private val _clientDataLiveData = MutableLiveData(Client())
    val clientDataLiveData: LiveData<Client> = _clientDataLiveData

    fun updateClientData(client: Client) {
        _clientDataLiveData.postValue(client)
    }
}