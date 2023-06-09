package com.autominder.autominder.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.AutoMinderApplication
import com.autominder.autominder.ui.components.BottomNavigationBar
import com.autominder.autominder.ui.components.TopBar
import com.autominder.autominder.ui.main.MainViewModel

@Composable
fun PrincipalScaffold() {
    val navController = rememberNavController()
    val navigationItem =
        listOf(Destinations.MyCars, Destinations.PrincipalMenu, Destinations.UserInfo)
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
    val topAppBarState = rememberSaveable { (mutableStateOf(false)) }

    when (navBackStackEntry.value?.destination?.route) {
        "my_cars" -> {
            bottomBarState.value = true
            topAppBarState.value = true
        }
        "register" -> {
            bottomBarState.value = false
            topAppBarState.value = false
        }
        "login" -> {
            bottomBarState.value = false
            topAppBarState.value = false
        }
        "welcome1" -> {
            bottomBarState.value = false
            topAppBarState.value = false
        }
        "welcome2" -> {
            bottomBarState.value = false
            topAppBarState.value = false
        }
        "welcome3" -> {
            bottomBarState.value = false
            topAppBarState.value = false
        }

        "principal_menu" -> {
            bottomBarState.value = true
            topAppBarState.value = true
        }

        "user_info" -> {
            bottomBarState.value = true
            topAppBarState.value = true
        }

        "car_info/" -> {
            bottomBarState.value = false
            topAppBarState.value = true
        }

        "change_password" -> {
            bottomBarState.value = false
            topAppBarState.value = true
        }

        "add_car" -> {
            bottomBarState.value = false
            topAppBarState.value = true
        }

    }


    Scaffold(
        topBar = {

            TopBar(navController, topAppBarState)
        },
        bottomBar = {
            BottomNavigationBar(
                navHostController = navController,
                items = navigationItem,
                bottomBarState
            )
        }

    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            NavigationHost(navController = navController)
        }
    }
}