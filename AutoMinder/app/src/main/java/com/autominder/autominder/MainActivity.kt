package com.autominder.autominder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.components.BottomNavigationBar
import com.autominder.autominder.components.TopBar
import com.autominder.autominder.navigation.Destinations
import com.autominder.autominder.navigation.NavigationHost
import com.autominder.autominder.ui.theme.AutoMinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoMinderTheme(useDarkTheme = isSystemInDarkTheme()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DefaultPreview()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    val navigationItem =
        listOf(Destinations.MyCars, Destinations.PrincipalMenu, Destinations.UserInfo)

    Scaffold(
        topBar = {
            //TopAppBar
                 TopBar()
        },
        bottomBar = {
            BottomNavigationBar(
                navHostController = navController,
                items = navigationItem,
            )
        }
    ) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            NavigationHost(navController = navController)
        }
    }
}