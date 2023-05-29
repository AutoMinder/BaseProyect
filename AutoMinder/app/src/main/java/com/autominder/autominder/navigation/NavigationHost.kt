package com.autominder.autominder.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.autominder.autominder.addcar.ui.AddCarScreen
import com.autominder.autominder.addcar.ui.AddCarViewModel
import com.autominder.autominder.carinfo.ui.CarInfoScreen
import com.autominder.autominder.forgotPassword.ForgotPasswordScreen
import com.autominder.autominder.login.ui.LoginScreen
import com.autominder.autominder.login.ui.LoginViewModel
import com.autominder.autominder.myCars.ui.MyCarsScreen
import com.autominder.autominder.myCars.ui.MyCarsViewModel
import com.autominder.autominder.obdSensor.ui.ObdSensorConnectScreen
import com.autominder.autominder.obdSensor.ui.ObdSensorViewModel
import com.autominder.autominder.principalMenu.ui.PrincipalMenuScreen
import com.autominder.autominder.register.RegisterScreen
import com.autominder.autominder.userInfo.UserInfoScreen
import com.autominder.autominder.userInfo.UserInfoViewModel
import com.autominder.autominder.userInfo.changePassword.ChangePasswordScreen
import com.autominder.autominder.userInfo.changePassword.ChangePasswordViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: String = "principal_menu",
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory
    ),
    addCarViewModel: AddCarViewModel = viewModel(
        factory = AddCarViewModel.Factory
    ),
    obdSensorViewModel: ObdSensorViewModel = viewModel(
        factory = ObdSensorViewModel.Factory
    )
) {

    NavHost(
        navController = navController,
        modifier = Modifier.padding(8.dp),
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
        composable("my_cars") {
            MyCarsScreen(navController)
        }
        composable("user_info") {
            UserInfoScreen(navController, UserInfoViewModel())
        }
        composable("change_password") {
            ChangePasswordScreen(ChangePasswordViewModel())
        }
        composable("car_info/{carId}",
            arguments = listOf(
                navArgument("carId") {
                    type = NavType.StringType
                }
            )
        ) {
            val carId = it.arguments?.getString("carId")
            if (carId != null) {
                val car = viewModel.fetchCarById(carId)
                if (car != null) {
                    CarInfoScreen(car, navController = navController)
                }
            }


        }
        composable("add_car") {
            AddCarScreen(viewModel = addCarViewModel, navController = navController)
        }
        composable("obd_sensor") {
            ObdSensorConnectScreen(obdSensorViewModel, navController)
        }
    }
}
