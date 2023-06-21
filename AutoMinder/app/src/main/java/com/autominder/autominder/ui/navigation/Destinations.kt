package com.autominder.autominder.ui.navigation

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

    object WelcomeScreen1 : Destinations("welcome1", "Welcome1", Icons.Filled.Home)
    object WelcomeScreen2 : Destinations("welcome2", "Welcome2", Icons.Filled.Home)
    object WelcomeScreen3 : Destinations("welcome3", "Welcome3", Icons.Filled.Home)
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

    object CarInfo : Destinations("car_info", "Car Info", Icons.Filled.Add)

    object AddCar : Destinations("add_car", "Add Car", Icons.Filled.Add)

}
