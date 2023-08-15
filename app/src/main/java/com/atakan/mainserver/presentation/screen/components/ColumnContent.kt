package com.atakan.mainserver.presentation.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CurrencyBitcoin
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakan.mainserver.constants.Colors
import com.atakan.mainserver.data.local.formatDoubleWithCommas
import com.atakan.mainserver.data.local.model.UserWallet


@Composable
fun ColumnContent(
    title: String,
    visibilityState: Boolean,
    onVisibilityToggle: () -> Unit,
    walletCurrent: UserWallet
) {
    val page = title == "Your USD Balance"
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 5.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.4f))
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (visibilityState) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                contentDescription = null,
                tint = Colors.primaryWhite.copy(alpha = 0.8f),
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
                    .clickable(onClick = onVisibilityToggle)
            )
        }

        // Here goes the balance info
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = if (page) Icons.Rounded.AttachMoney else Icons.Outlined.CurrencyBitcoin,
                contentDescription = null,
                tint = Colors.primaryWhite,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp)
            )
            Text(
                text = if (visibilityState) {
                    if (page)
                        formatDoubleWithCommas(walletCurrent.balanceUSD)
                    else
                        formatDoubleWithCommas(walletCurrent.balanceBTC)
                } else "*** *** ***",
                style = TextStyle(color = Colors.primaryWhite, fontSize = 35.sp)
            )
        }
    }
}