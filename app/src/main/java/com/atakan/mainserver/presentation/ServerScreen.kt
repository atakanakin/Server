package com.atakan.mainserver.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.atakan.mainserver.data.model.MyApplicationHolder


@Composable
fun ServerScreen(viewModel: ClientDataViewModel = MyApplicationHolder.getViewModel()) {

    // Observe changes to the LiveData and convert it to a Composable state
    val clientState by viewModel.clientDataLiveData.observeAsState()

    clientState?.clientCurr1?.let { Text(text = it) }
    clientState?.clientCurr2.let {  }
}