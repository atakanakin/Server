package com.atakan.mainserver.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun IndicatorDots(
    itemCount: Int,
    currentPage: Int
) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(itemCount) { pageIndex ->
            Spacer(modifier = Modifier.width(pageIndex.dp * 10))
            Dot(isActive = pageIndex == currentPage)
        }
    }
}

@Composable
private fun Dot(
    isActive: Boolean
) {
    val color = if (isActive) Color.White else Color.Gray
    val size = if (isActive) 8.dp else 6.dp
    Box(
        modifier = Modifier
            .size(size)
            .clip(shape = RoundedCornerShape(3.dp))
            .background(color = color)
            .padding(4.dp)

    )
}
