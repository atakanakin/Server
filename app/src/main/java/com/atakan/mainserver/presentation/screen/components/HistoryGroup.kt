package com.atakan.mainserver.presentation.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atakan.mainserver.data.local.model.History


@Composable
fun HistoryGroup(historyItems: List<History>) {
    LazyColumn {
        items(historyItems.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { item ->
                    HistoryCard(
                        date = item.date,
                        action = item.action,
                        amountBTC = item.amountBTC,
                        amountUSD = item.amountUSD
                    )
                }
            }
        }
    }
}
