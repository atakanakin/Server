package com.atakan.mainserver

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.atakan.mainserver.data.model.MyApplicationHolder
import com.atakan.mainserver.presentation.ClientDataViewModel

class MyApplication : Application() {
    val clientDataViewModel: ClientDataViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this).create(ClientDataViewModel::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        MyApplicationHolder.init(applicationContext, clientDataViewModel)
    }
}
