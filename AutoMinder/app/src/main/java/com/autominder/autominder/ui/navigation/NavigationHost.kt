package com.autominder.autominder.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.autominder.autominder.ui.addcar.ui.AddCarScreen
import com.autominder.autominder.ui.addcar.ui.AddCarViewModel
import com.autominder.autominder.ui.carinfo.ui.CarInfoScreen
import com.autominder.autominder.ui.forgotPassword.ForgotPasswordScreen
import com.autominder.autominder.ui.login.ui.LoginScreen
import com.autominder.autominder.ui.myCars.ui.MyCarsScreen
import com.autominder.autominder.ui.myCars.ui.MyCarsViewModel
import com.autominder.autominder.obdApiSensor.ui.ObdReader
import com.autominder.autominder.obdApiSensor.ui.ObdSensorConnectScreen
import com.autominder.autominder.obdApiSensor.ui.ObdSensorViewModel
import com.autominder.autominder.ui.principalMenu.ui.PrincipalMenuScreen
import com.autominder.autominder.ui.register.RegisterScreen
import com.autominder.autominder.ui.userInfo.UserInfoScreen
import com.autominder.autominder.ui.userInfo.UserInfoViewModel
import com.autominder.autominder.ui.userInfo.changePassword.ChangePasswordScreen
import com.autominder.autominder.ui.userInfo.changePassword.ChangePasswordViewModel

@Composable
fun NavigationHost(
    navController: NavHostController,
    startDestination: String = "login",
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
    val coroutineScope = rememberCoroutineScope()
    NavHost(
        navController = navController,
        modifier = Modifier.padding(8.dp),
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
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
            DisposableEffect(carId){
                val car = viewModel.fetchCarById(carId!!)
                onDispose {
                    viewModel.clearCarInfo()
                }
            }



        }
        composable("add_car") {
            AddCarScreen(viewModel = addCarViewModel, navController = navController)
        }
        composable("obd_sensor") {
            ObdSensorConnectScreen(obdSensorViewModel, navController)
        }
        composable("obd_reader") {
            ObdReader(obdSensorViewModel = obdSensorViewModel, bluetoothDevice = obdSensorViewModel.bluetoothDevice)

        }
    }
}
