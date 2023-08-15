package com.atakan.mainserver.data.local.model

data class History(
    val date: String,
    val action: String,
    val amountBTC: String,
    val amountUSD: String
)

data class UserHistory(
    val history: List<History>
)