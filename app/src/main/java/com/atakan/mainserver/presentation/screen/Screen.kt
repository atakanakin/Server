package com.atakan.mainserver.presentation.screen

sealed class Screen (val route: String){
    object MainScreen: Screen("main_screen")
    object ServerScreen: Screen("server_screen")
}