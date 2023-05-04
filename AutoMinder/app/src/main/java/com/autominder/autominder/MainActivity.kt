package com.autominder.autominder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.components.BottomNavigationBar
import com.autominder.autominder.forgotPassword.ForgotPasswordScreen
import com.autominder.autominder.login.ui.LoginScreen
import com.autominder.autominder.login.ui.LoginViewModel
import com.autominder.autominder.navigation.Destinations
import com.autominder.autominder.navigation.NavigationHost
import com.autominder.autominder.principalMenu.PrincipalMenuScreen
import com.autominder.autominder.register.RegisterScreen
import com.autominder.autominder.ui.theme.AutoMinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoMinderTheme(useDarkTheme = false) {
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
    val navigationItem = listOf(Destinations.PrincipalMenu, Destinations.Login, Destinations.Register)

    Scaffold(
        topBar = {
            //TopAppBar
        },
        bottomBar = { BottomNavigationBar(navHostController = navController, items = navigationItem, )  }
    ) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) {
            NavigationHost(navController = navController)
        }
    }
}