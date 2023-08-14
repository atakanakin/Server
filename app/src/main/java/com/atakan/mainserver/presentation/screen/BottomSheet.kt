package com.atakan.mainserver.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetScaffoldExample() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(

        sheetContent = {
            Column {
                Text("Hey Atakan")
            }
        },
        scaffoldState = bottomSheetScaffoldState,

        sheetPeekHeight  =  100.dp, // Adjust as needed

        content = {
            // Your main content goes here
            // You can also add a button to open the bottom sheet
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BottomSheetScaffoldPreview() {
    BottomSheetScaffoldExample()
}
