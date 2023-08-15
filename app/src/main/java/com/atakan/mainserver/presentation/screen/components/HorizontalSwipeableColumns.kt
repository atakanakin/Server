package com.atakan.mainserver.presentation.screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.atakan.mainserver.data.local.model.History
import com.atakan.mainserver.data.local.model.UserWallet


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalSwipeableColumns(
    state: PagerState,
    historyItems: List<History>,
    walletCurrent: UserWallet,
    visibilityState: Boolean,
    onVisibilityToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(100.dp)
    ) {
        HorizontalPager(
            state = state
        ) { pageIndex ->
            // Create your content for each column
            val content = when (pageIndex) {
                0 -> {
                    // First column content
                    ColumnContent(
                        "Your USD Balance",
                        visibilityState,
                        onVisibilityToggle,
                        walletCurrent
                    )
                }
                1 -> {
                    // Second column content
                    ColumnContent(
                        "Your BTC Balance",
                        visibilityState,
                        onVisibilityToggle,
                        walletCurrent
                    )
                }
                else -> {
                    // Fallback content
                    Text("Page $pageIndex")
                }
            }

            // Wrap content with scrolling for each column
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { _, _ ->
                            // Consume the drag gestures to avoid conflicts with scrolling
                        }
                    }
            ) {
                content
            }
        }
    }
}