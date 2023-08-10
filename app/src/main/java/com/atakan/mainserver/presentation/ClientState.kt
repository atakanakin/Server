package com.atakan.mainserver.presentation

import com.atakan.mainserver.data.model.Client

data class ClientState (
    val client : Client = Client(null, null, null, null, null, null, null, null, null, "no"),
    val isBound : String = ""
)