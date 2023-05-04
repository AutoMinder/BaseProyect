package com.autominder.autominder.navigation

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {

    object Login : Destinations("login", "Login", Icons.Filled.Home)
    object MyCars : Destinations("my_cars", "My Cars", Icons.Filled.Add)
    object UserInfo : Destinations("user_info", "User info", Icons.Filled.Person)
    object Register : Destinations("register", "Register", Icons.Filled.Person)
    object ForgotPassword : Destinations("forgot_password",
        "Forgot Password", Icons.Filled.Add) /*TODO Add the correct vector*/
        object PrincipalMenu : Destinations("principal_menu", "Principal Menu", Icons.Filled.Home)

        //fun createRoute(route: String) = "principal_menu/$route"
}
