package com.atakan.mainserver.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CurrencyBitcoin
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakan.mainserver.constants.Colors


@Composable
fun HistoryCard(
    date: String,
    action: String,
    amountBTC: String,
    amountUSD: String
) {
    var isBought: Boolean = action == "Bought BTC"
    val (height, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }
    Box(
        modifier = Modifier
            .height(((width - 50.dp) / 14) * 5)
            .width(((width - 70.dp) / 2))
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp))
            .background(color = Colors.primaryWhite.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp, top = 20.dp)
        ) {
            Text(modifier = Modifier
                .fillMaxWidth(),
                text = action, style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.8f), fontSize = 17.sp), textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.weight(0.1f))
            Text(modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
                text = date, style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.3f), fontSize = 10.sp), textAlign = TextAlign.End)
            Spacer(modifier = Modifier.weight(0.6f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = if(!isBought) Icons.Outlined.CurrencyBitcoin else Icons.Outlined.AttachMoney, contentDescription = null,tint = Colors.primaryWhite,
                    modifier = Modifier
                        .height(15.dp)
                        .width(15.dp))

                Text(text = if(!isBought) amountBTC else amountUSD, style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.8f), fontSize = 15.sp))

                Icon(imageVector = Icons.Outlined.Sync, contentDescription = null, tint = Colors.primaryWhite)

                Icon(imageVector = if(isBought) Icons.Outlined.CurrencyBitcoin else Icons.Outlined.AttachMoney, contentDescription = null,tint = Colors.primaryWhite,
                    modifier = Modifier
                        .height(15.dp)
                        .width(15.dp))

                Text(text = if(isBought) amountBTC else amountUSD, style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.8f), fontSize = 15.sp))
            }
        }
    }
}