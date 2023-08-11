package com.atakan.mainserver.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.atakan.mainserver.data.model.MyApplicationHolder
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun ServerScreen(viewModel: ClientDataViewModel = MyApplicationHolder.getViewModel()) {

    // Observe changes to the LiveData and convert it to a Composable state
    val clientState by viewModel.clientDataLiveData.observeAsState()
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
        Text(text = clientState?.clientRate1?.toString() ?: "No message")
    }

}