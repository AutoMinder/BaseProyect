package com.autominder.autominder.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.autominder.autominder.forgotPassword.ForgotPasswordScreen
import com.autominder.autominder.login.ui.LoginScreen
import com.autominder.autominder.login.ui.LoginViewModel
import com.autominder.autominder.myCars.ui.MyCarsScreen
import com.autominder.autominder.principalMenu.PrincipalMenuScreen
import com.autominder.autominder.register.RegisterScreen
import com.autominder.autominder.userInfo.UserInfoScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController, /*TODO add conditional to decide which is the host*/
    startDestination: String = "principal_menu"
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
        composable("principal_menu") {
            PrincipalMenuScreen()
        }
        composable("my_cars"){
            MyCarsScreen()
        }
        composable("user_info"){
            UserInfoScreen()
        }
    }
}