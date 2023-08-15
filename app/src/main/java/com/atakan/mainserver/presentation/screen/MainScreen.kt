package com.atakan.mainserver.presentation.screen

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CurrencyBitcoin
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakan.mainserver.constants.Colors
import com.atakan.mainserver.data.local.formatDoubleWithCommas
import com.atakan.mainserver.data.local.getGreeting
import com.atakan.mainserver.data.local.model.History
import com.atakan.mainserver.data.local.model.UserHistory
import com.atakan.mainserver.data.local.model.UserWallet
import com.atakan.mainserver.data.local.readJson
import com.atakan.mainserver.presentation.screen.components.HistoryGroup
import com.atakan.mainserver.presentation.screen.components.HorizontalSwipeableColumns
import com.atakan.mainserver.presentation.screen.components.IndicatorDots
import com.google.gson.Gson
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context) {
    val (height, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }
    var visibilityState by remember{ mutableStateOf(false) }


    val jsonHistory = readJson("userHistory.json")
    var historyItems = Gson().fromJson(jsonHistory, UserHistory::class.java).history

    val jsonWallet = readJson("wallet.json")
    var walletCurrent = Gson().fromJson(jsonWallet, UserWallet::class.java)

    var fileState by remember { mutableStateOf(historyItems) }

    var balanceState by remember { mutableStateOf(walletCurrent) }

    var pagerState = rememberPagerState { 2 }

    // Define a mutable state variable to track if the dialog is open
    var isConvertDialogOpen by remember { mutableStateOf(false) }

    val exchangeRate = 1.0
    val bottomSheetState = rememberBottomSheetScaffoldState(SheetState(skipPartiallyExpanded = false, skipHiddenState = false))
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    BottomSheetScaffold(

        sheetSwipeEnabled = true,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            var amount: String by remember { mutableStateOf("") }
            val equivalentAmount = amount.toDoubleOrNull()?.times(exchangeRate) ?: 0.0
            DisposableEffect(keyboardController) {
                onDispose {
                    keyboardController?.hide()
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .height(height - 200.dp)
                    .fillMaxSize()
                    .clickable {
                        keyboardController?.hide()
                    }
            ) {
                Box(modifier = Modifier.padding(20.dp)) {
                    OutlinedTextField(
                        leadingIcon = {
                            Icon(
                                imageVector = if (pagerState.currentPage == 0) Icons.Rounded.AttachMoney else Icons.Outlined.CurrencyBitcoin,
                                contentDescription = null,
                                tint = Colors.primaryBlack
                            )
                        },
                        label = { Text(text = "Enter Amount") },
                        modifier = Modifier.background(Colors.primaryWhite),
                        value = amount,
                        onValueChange = { newValue ->
                            amount = newValue
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        )
                    )
                }
                Box(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (pagerState.currentPage == 1) Icons.Rounded.AttachMoney else Icons.Outlined.CurrencyBitcoin,
                        contentDescription = null,
                        tint = Colors.primaryBlack
                    )
                    Text(
                        text = equivalentAmount.toString(),
                        style = TextStyle(color = Colors.primaryBlack)
                    )
                }}
                Box(modifier = Modifier.align(Alignment.CenterHorizontally).clip(shape = RoundedCornerShape(20.dp)).background(color = Colors.primaryGreen).clickable {  }.padding(20.dp)) {
                    Text(
                        text = "Convert",
                        style = TextStyle(color = Colors.primaryWhite)
                    )
                }
            }
        },
        scaffoldState = bottomSheetState
    ) {
        if (bottomSheetState.bottomSheetState.hasExpandedState) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable {
                    keyboardController?.hide()
                    scope.launch {
                        bottomSheetState.bottomSheetState.hide()
                    }
                }
        )
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Colors.primaryBlack)
        ) {
            Box(
                modifier = Modifier
                    .height(height / 2)
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(0.dp, 0.dp, 50.dp, 50.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Colors.primaryGreen,
                                Colors.gradientGreen
                            )
                        )
                    )
                    .padding(top = 30.dp)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Top
                ) {
                    // Icon Row
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Rounded.DragHandle,
                            contentDescription = null,
                            tint = Colors.primaryWhite,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                        Column(modifier = Modifier.padding(start = 20.dp)) {
                            Text(
                                text = getGreeting(),
                                style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.4f))
                            )
                            Text(
                                text = walletCurrent.preferredName,
                                style = TextStyle(color = Colors.primaryWhite)
                            ) // Change it with preferred name in UserWallet object
                        }
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Rounded.NotificationsActive,
                            contentDescription = null,
                            tint = Colors.primaryWhite,
                            modifier = Modifier
                                .height(25.dp)
                                .width(25.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.7f))
                    // total balance string row

                    HorizontalSwipeableColumns(
                        pagerState,
                        historyItems,
                        walletCurrent,
                        visibilityState
                    ) {
                        visibilityState = visibilityState.not()
                    }
                    // Add indicator dots
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        IndicatorDots(pagerState.pageCount, pagerState.currentPage)
                    }
                    Spacer(modifier = Modifier.weight(0.3f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier
                            .height(90.dp)
                            .width((width - 50.dp) / 2)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp))
                            .background(color = Colors.primaryBlack)
                            .clickable {
                                scope.launch {
                                    bottomSheetState.bottomSheetState.expand()
                                }
                                /*
                            val mutableList = historyItems.toMutableList()
                            mutableList.add(0,History(
                                "03/08/2023",
                                "Bought BTC",
                                "0.02",
                                "342.45")
                            )
                            val written = UserHistory(
                                mutableList
                            )
                            writeJson("userHistory.json", written)
                            val updatedJsonString = readJson("userHistory.json")
                            historyItems =
                                Gson().fromJson(updatedJsonString, UserHistory::class.java).history
                            println(historyItems.size)
                            fileState = historyItems
                            */
                            }
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentHeight(),
                                text = "Convert",
                                style = TextStyle(color = Colors.primaryWhite, fontSize = 20.sp),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Box(modifier = Modifier
                            .height(90.dp)
                            .width((width - 50.dp) / 2)
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp))
                            .background(color = Colors.primaryWhite)
                            .clickable {

                            }
                        )
                        {
                            Text(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentHeight(),
                                text = "Details",
                                style = TextStyle(color = Colors.primaryBlack, fontSize = 20.sp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        "History",
                        style = TextStyle(color = Colors.primaryWhite, fontSize = 20.sp)
                    )
                }
                HistoryGroup(historyItems = fileState)
            }
        }
    }
}
