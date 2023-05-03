package com.autominder.autominder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.forgotPassword.ForgotPasswordScreen
import com.autominder.autominder.login.ui.LoginScreen
import com.autominder.autominder.login.ui.LoginViewModel
import com.autominder.autominder.principalMenu.PrincipalMenuScreen
import com.autominder.autominder.register.RegisterScreen
import com.autominder.autominder.ui.theme.AutoMinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun MyAppHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(), /*TODO add conditional to decide which is the host*/
    startDestination: String = "login"
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(LoginViewModel(), navController)
        }
        composable("register") {
            RegisterScreen()
        }
        composable("forgot_password") {
            ForgotPasswordScreen()
        }
        composable("principal_menu"){
            PrincipalMenuScreen()
        }
    }
}