package com.atakan.mainserver.presentation.screen

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.atakan.mainserver.R
import com.atakan.mainserver.constants.Colors
import compose.icons.AllIcons
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Github


@Composable
fun deneme(context: Context) {
    val (height, width) = LocalConfiguration.current.run { screenHeightDp.dp to screenWidthDp.dp }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Colors.primaryBlack)){
        println(height)
        println(width)
        Box(modifier = Modifier
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
        ){
            Column(modifier = Modifier
                .fillMaxWidth(),
                verticalArrangement = Arrangement.Top
                ){
                // Icon Row
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        imageVector = Icons.Rounded.DragHandle,
                        contentDescription = null,
                        tint = Colors.primaryWhite,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                    Column(modifier = Modifier.padding(start = 20.dp)) {
                        Text(text ="Good Morning,", style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.4f)))
                        Text(text = "Atakan") // Change it with preferred name in UserWallet object
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
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 5.dp)) {
                    Text(text = "Your Total Balance", style = TextStyle(color = Colors.primaryWhite.copy(alpha = 0.4f)))
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Outlined.VisibilityOff,
                        contentDescription = null,
                        tint = Colors.primaryWhite.copy(alpha = 0.8f),
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(0.1f))
                // Here goes the balance info
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        imageVector = Icons.Rounded.AttachMoney,
                        contentDescription = null,
                        tint = Colors.primaryWhite,
                        modifier = Modifier
                            .height(40.dp)
                            .width(40.dp)
                    )
                    Text(text = "25,678.866", style = TextStyle(color = Colors.primaryWhite, fontSize = 35.sp))
                }
                Spacer(modifier = Modifier.weight(0.3f))
                Row(verticalAlignment = Alignment.CenterVertically){
                    Box(modifier = Modifier
                        .height(90.dp)
                        .width((width - 50.dp) / 2)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp))
                        .background(color = Colors.primaryBlack)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier
                        .height(90.dp)
                        .width((width - 50.dp) / 2)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(35.dp, 35.dp, 35.dp, 35.dp))
                        .background(color = Colors.primaryWhite)
                    )
                }
            }
        }
    }
}
