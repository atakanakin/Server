package com.atakan.mainserver.presentation.screen

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.atakan.mainserver.constants.Colors
import com.atakan.mainserver.data.local.formatDoubleWithCommas
import com.atakan.mainserver.data.local.getCurrentDateFormatted
import com.atakan.mainserver.data.local.getGreeting
import com.atakan.mainserver.data.local.model.History
import com.atakan.mainserver.data.local.model.UserHistory
import com.atakan.mainserver.data.local.model.UserWallet
import com.atakan.mainserver.data.local.readJson
import com.atakan.mainserver.data.local.writeJson
import com.atakan.mainserver.presentation.ClientDataViewModel
import com.atakan.mainserver.presentation.screen.components.HistoryGroup
import com.atakan.mainserver.presentation.screen.components.HorizontalSwipeableColumns
import com.atakan.mainserver.presentation.screen.components.IndicatorDots
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context, viewModel: ClientDataViewModel = hiltViewModel()) {
    var boolPage = false
    var isDetail by remember {
        mutableStateOf(boolPage)
    }

    val currencyState by viewModel.clientDataLiveData.observeAsState()

    val (height, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }
    var visibilityState by remember{ mutableStateOf(false) }

    val jsonHistory = readJson("userHistory.json")
    var historyItems = Gson().fromJson(jsonHistory, UserHistory::class.java).history

    val jsonWallet = readJson("wallet.json")
    var walletCurrent = Gson().fromJson(jsonWallet, UserWallet::class.java)

    var fileState by remember { mutableStateOf(historyItems) }

    var balanceState by remember { mutableStateOf(walletCurrent) }

    var pagerState = rememberPagerState { 2 }



    val bottomSheetState = rememberBottomSheetScaffoldState(SheetState(skipPartiallyExpanded = false, skipHiddenState = false))

    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetSwipeEnabled = true,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            key(currencyState)
            {
                if (currencyState != null && currencyState?.clientCurr1 != null) {
                    if(!isDetail){
                        var exchangeRate =
                            if (pagerState.currentPage == 0) 1 / currencyState!!.clientRate1!! else currencyState!!.clientRate1!!
                        var amount: String by remember { mutableStateOf("") }
                        val equivalentAmount =
                            amount.toDoubleOrNull()?.times(exchangeRate).toString().toDoubleOrNull()
                                ?: 0.0
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
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .clip(shape = RoundedCornerShape(20.dp))
                                    .background(color = Colors.primaryGreen)
                                    .clickable {
                                        if (pagerState.currentPage == 0) {
                                            if ((amount.toDoubleOrNull()
                                                    ?: 0.0) > walletCurrent.balanceUSD
                                            ) {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Not enough currency",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            } else {
                                                val written = walletCurrent.copy(
                                                    balanceBTC = walletCurrent.balanceBTC + equivalentAmount,
                                                    balanceUSD = walletCurrent.balanceUSD - amount.toDouble()
                                                )
                                                writeJson("wallet.json", written)
                                                val updateRead = readJson("wallet.json")
                                                walletCurrent =
                                                    Gson().fromJson(
                                                        updateRead,
                                                        UserWallet::class.java
                                                    )
                                                balanceState = walletCurrent
                                                keyboardController?.hide()
                                                scope.launch {
                                                    bottomSheetState.bottomSheetState.hide()
                                                }
                                                // Write History
                                                val mutableList = historyItems.toMutableList()
                                                mutableList.add(
                                                    0, History(
                                                        getCurrentDateFormatted(),
                                                        "Bought BTC",
                                                        equivalentAmount
                                                            .toString()
                                                            .toDoubleOrNull()
                                                            .toString(),
                                                        amount
                                                            .toDoubleOrNull()
                                                            .toString()
                                                    )
                                                )
                                                val writtenHistory = UserHistory(
                                                    mutableList
                                                )
                                                writeJson("userHistory.json", writtenHistory)
                                                val updatedJsonString = readJson("userHistory.json")
                                                historyItems =
                                                    Gson().fromJson(
                                                        updatedJsonString,
                                                        UserHistory::class.java
                                                    ).history
                                                fileState = historyItems
                                                //
                                                amount = ""
                                            }
                                        } else {
                                            if ((amount.toDoubleOrNull()
                                                    ?: 0.0) > walletCurrent.balanceBTC
                                            ) {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Not enough currency",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            } else {
                                                val written = walletCurrent.copy(
                                                    balanceBTC = walletCurrent.balanceBTC - amount.toDouble(),
                                                    balanceUSD = walletCurrent.balanceUSD + equivalentAmount
                                                )
                                                writeJson("wallet.json", written)
                                                val updateRead = readJson("wallet.json")
                                                walletCurrent =
                                                    Gson().fromJson(
                                                        updateRead,
                                                        UserWallet::class.java
                                                    )
                                                balanceState = walletCurrent
                                                keyboardController?.hide()
                                                scope.launch {
                                                    bottomSheetState.bottomSheetState.hide()
                                                }
                                                // Write History
                                                val mutableList = historyItems.toMutableList()
                                                mutableList.add(
                                                    0, History(
                                                        getCurrentDateFormatted(),
                                                        "Sold BTC",
                                                        amount
                                                            .toDoubleOrNull()
                                                            .toString(),
                                                        equivalentAmount
                                                            .toString()
                                                            .toDoubleOrNull()
                                                            .toString()
                                                    )
                                                )
                                                val writtenHistory = UserHistory(
                                                    mutableList
                                                )
                                                writeJson("userHistory.json", writtenHistory)
                                                val updatedJsonString = readJson("userHistory.json")
                                                historyItems =
                                                    Gson().fromJson(
                                                        updatedJsonString,
                                                        UserHistory::class.java
                                                    ).history
                                                fileState = historyItems
                                                //
                                                amount = ""
                                            }
                                        }
                                    }
                                    .padding(20.dp)
                            ) {
                                Text(
                                    text = "Convert",
                                    style = TextStyle(color = Colors.primaryWhite)
                                )
                            }
                        }
                    }
                    else{
                        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)) {
                            val initialDateString = currencyState!!.time.toString()
                            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm:ss z", Locale.ENGLISH)
                            sdf.timeZone = TimeZone.getTimeZone("UTC")

                            val initialDate = sdf.parse(initialDateString)
                            val calendar = Calendar.getInstance()
                            calendar.time = initialDate

                            sdf.timeZone = TimeZone.getTimeZone("GMT+3")
                            val finalDateString = sdf.format(calendar.time)
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                                Text(text = "Package Received", textAlign = TextAlign.Center)
                                Text(text = finalDateString, textAlign = TextAlign.Center)
                            }

                            Text(text = "${ currencyState!!.clientCurr1.toString() }      ${currencyState!!.clientRate1.toString()}")


                            Text(text = "${ currencyState!!.clientCurr2.toString() }      ${currencyState!!.clientRate2.toString()}")

                            Text(text = "${ currencyState!!.clientCurr3.toString() }      ${currencyState!!.clientRate3.toString()}")

                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                                Text(text = "IPC Method", textAlign = TextAlign.Center)
                                Text(text = currencyState!!.ipcMethod, textAlign = TextAlign.Center)
                            }
                            //val bottomSheetState = rememberBottomSheetScaffoldState()
                            //val scope = rememberCoroutineScope()

                            Button(onClick = {
                                Toast.makeText(context, currencyState!!.clientPackageName, Toast.LENGTH_SHORT).show()
                            }, modifier = Modifier.align(Alignment.CenterHorizontally
                            )) {
                                Text(text = "Details")
                            }
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("NO SERVER CONNECTION", textAlign = TextAlign.Center)
                    }

                }
            }
        },
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
                        walletCurrent,
                        visibilityState,
                        balanceState
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
                                isDetail = false
                                scope.launch {
                                    bottomSheetState.bottomSheetState.expand()
                                }
                                /*

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
                                isDetail = true
                                scope.launch {
                                    bottomSheetState.bottomSheetState.expand()
                                }
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
