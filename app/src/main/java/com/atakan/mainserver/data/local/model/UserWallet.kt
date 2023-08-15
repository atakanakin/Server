package com.atakan.mainserver.data.local.model

data class UserWallet(
    val username : String,
    val preferredName: String,
    val balanceBTC : Double,
    val balanceUSD : Double
)