package com.autominder.autominder.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.autominder.autominder.R


sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    object Login : Destinations("login", "Login", Icons.Filled.Home)
    object MyCars : Destinations("my_cars", "My Cars", Icons.Filled.Add)
    object UserInfo : Destinations("user_info", "User info", Icons.Filled.Person)
    object Register : Destinations("register", "Register", Icons.Filled.Person)
    object ForgotPassword : Destinations(
        "forgot_password",
        "Forgot Password", Icons.Filled.Add
    ) /*TODO Add the correct vector*/

    object PrincipalMenu : Destinations("principal_menu", "Principal Menu", Icons.Filled.Home)

    object ChangePassword : Destinations("change_password", "Change Password", Icons.Filled.Add)

}
