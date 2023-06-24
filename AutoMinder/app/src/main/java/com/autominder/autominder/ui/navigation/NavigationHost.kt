package com.autominder.autominder.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.autominder.autominder.data.database.models.CarModel
import com.autominder.autominder.ui.addcar.ui.AddCarScreen
import com.autominder.autominder.ui.addcar.ui.AddCarViewModel
import com.autominder.autominder.ui.carinfo.ui.CarInfoScreen
import com.autominder.autominder.ui.forgotPassword.ForgotPasswordScreen
import com.autominder.autominder.ui.login.ui.LoginScreen
import com.autominder.autominder.ui.myCars.ui.MyCarsScreen
import com.autominder.autominder.ui.myCars.ui.MyCarsViewModel

import com.autominder.autominder.obdApiSensor.ui.ObdSensorConnectScreen
import com.autominder.autominder.obdApiSensor.ui.ObdSensorViewModel
import com.autominder.autominder.ui.main.MainViewModel
import com.autominder.autominder.ui.principalMenu.ui.PrincipalMenuScreen
import com.autominder.autominder.ui.register.RegisterScreen
import com.autominder.autominder.ui.userInfo.UserInfoScreen
import com.autominder.autominder.ui.userInfo.UserInfoViewModel
import com.autominder.autominder.ui.userInfo.changePassword.ChangePasswordScreen
import com.autominder.autominder.ui.userInfo.changePassword.ChangePasswordViewModel
import com.autominder.autominder.ui.welcomeScreens.FirstScreen
import com.autominder.autominder.ui.welcomeScreens.SecondScreen
import com.autominder.autominder.ui.welcomeScreens.ThirdScreen


@Composable
fun NavigationHost(
    navController: NavHostController,

    mainViewModel: MainViewModel = viewModel(),

    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory
    ),
    addCarViewModel: AddCarViewModel = viewModel(
        factory = AddCarViewModel.Factory
    ),
    obdSensorViewModel: ObdSensorViewModel = viewModel(
        factory = ObdSensorViewModel.Factory
    ),
    userInfoViewModel: UserInfoViewModel = viewModel(
        factory = UserInfoViewModel.Factory
    ),
) {
    val startDestination = mainViewModel.startDestination.collectAsState()

    NavHost(
        navController = navController,
        modifier = Modifier.padding(8.dp),
        startDestination = startDestination.value
    ) {
        composable("welcome1") {
            FirstScreen(navController = navController)
        }
        composable("welcome2") {
            SecondScreen(navController = navController)
        }
        composable("welcome3") {
            ThirdScreen(navController = navController)
        }
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
            UserInfoScreen(navController, userInfoViewModel)
        }
        composable("change_password") {
            ChangePasswordScreen(ChangePasswordViewModel())
        }
        /*composable("car_info/{carId}",
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


        }*/
        composable("car_info") {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<CarModel>("car")
            if (result != null) {
                CarInfoScreen(car = result, navController = navController)
            }
        }
        composable("add_car") {
            AddCarScreen(viewModel = addCarViewModel, navController = navController)
        }
        composable("obd_sensor") {
            ObdSensorConnectScreen(obdSensorViewModel)
        }
    }
}
