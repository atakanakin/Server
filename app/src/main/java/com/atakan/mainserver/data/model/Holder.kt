package com.atakan.mainserver.data.model

import android.annotation.SuppressLint
import android.content.Context
import com.atakan.mainserver.presentation.ClientDataViewModel

@SuppressLint("StaticFieldLeak")
object MyApplicationHolder {
    private var context: Context? = null
    private lateinit var viewModel: ClientDataViewModel

    fun init(context: Context, viewModel: ClientDataViewModel) {
        this.context = context.applicationContext
        this.viewModel = viewModel
    }

    @SuppressLint("StaticFieldLeak")
    fun getApplicationContext(): Context? {
        return context
    }

    fun getViewModel(): ClientDataViewModel {
        return viewModel
    }
}