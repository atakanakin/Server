package com.atakan.mainserver.presentation.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.atakan.mainserver.data.model.MyApplicationHolder
import com.atakan.mainserver.presentation.ClientDataViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone


@Composable
fun ServerScreen(viewModel: ClientDataViewModel = MyApplicationHolder.getViewModel(), context: Context) {

    // Observe changes to the LiveData and convert it to a Composable state
    val clientState by viewModel.clientDataLiveData.observeAsState()


    if(clientState?.time == null){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
            Text(text = "No message")
        }
    }
    else{
        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)) {
            val initialDateString = clientState!!.time.toString()
            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm:ss z")
            sdf.timeZone = TimeZone.getTimeZone("UTC")

            val initialDate = sdf.parse(initialDateString)
            val calendar = Calendar.getInstance()
            calendar.time = initialDate

            sdf.timeZone = TimeZone.getTimeZone("GMT+3")
            val finalDateString = sdf.format(calendar.time)
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "Package Received", textAlign = TextAlign.Center)
                Text(text = finalDateString, textAlign = TextAlign.Center)
            }

            Text(text = "${ clientState!!.clientCurr1.toString() }      ${clientState!!.clientRate1.toString()}")


            Text(text = "${ clientState!!.clientCurr2.toString() }      ${clientState!!.clientRate2.toString()}")

            Text(text = "${ clientState!!.clientCurr3.toString() }      ${clientState!!.clientRate3.toString()}")
            
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "IPC Method", textAlign = TextAlign.Center)
                Text(text = clientState!!.ipcMethod, textAlign = TextAlign.Center)
            }

            Button(onClick = {
                Toast.makeText(context, "Package Name:     ${clientState!!.clientPackageName}\nProcess ID:     ${clientState!!.clientProcessId}", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier.align(Alignment.CenterHorizontally
            )) {
                Text(text = "Details")
            }
        }
    }


}