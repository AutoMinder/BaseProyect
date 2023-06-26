package com.autominder.autominder.ui.myCars.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.autominder.autominder.R
import com.autominder.autominder.data.database.models.CarModel
import com.autominder.autominder.ui.components.LoadingScreen

/**
 * Composable function representing the MyCars screen.
 *
 * @param navController The NavController for navigating between destinations.
 * @param viewModel The instance of MyCarsViewModel to manage the data and state of the screen.
 */
@Composable
fun MyCarsScreen(
    navController: NavController,
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory,
    )
) {
    val isLoading by viewModel.isLoading.collectAsState(false)

    Scaffold(
        floatingActionButton = { FloatingAddButtonCar(navController) },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.surface),
        ) {
            //Checks if is loading, if it is, it will display the loading screen, if not, it will display the main screen
            if (isLoading) {
                LoadingScreen()

            } else {
                MainScreenCars(viewModel, navController)
            }
        }
    }
}

@Composable
fun FloatingAddButtonCar(navController: NavController) {
    androidx.compose.material3.FloatingActionButton(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(Alignment.BottomEnd),
        onClick = { navController.navigate("add_car") },
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add button")
    }
}

/**
 * Composable function representing the floating action button for adding a car.
 *
 * @param navController The NavController for navigating to the "add_car" destination.
 */

/**
 * Composable function representing the main screen for displaying cars.
 *
 * @param viewModel The instance of MyCarsViewModel to manage the data and state of the screen.
 * @param navController The NavController for navigating between destinations.
 */
@Composable
fun MainScreenCars(viewModel: MyCarsViewModel, navController: NavController?) {
    val car2 = remember {
        viewModel.getCars()
    }
    val cars = car2.collectAsLazyPagingItems()

    PagingMyCars(cars, navController)
}

/**
 * Composable function representing the paging list of cars.
 *
 * @param cars The LazyPagingItems representing the cars data.
 * @param navController The NavController for navigating to the "car_info" destination.
 */
@Composable
fun PagingMyCars(
    cars: LazyPagingItems<CarModel>,
    navController: NavController?,
    ) {

    val scrollState = rememberLazyGridState(0)
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 350.dp),
        state = scrollState,
    ) {
        cars.apply {
            when {
                loadState.refresh is LoadState.Loading -> println("Estoy cargando en refresh")
                loadState.append is LoadState.Loading -> println("Estoy cargando en append")
                loadState.append is LoadState.Error -> println(" Estoy en error")
            }
        }

        if(cars.itemCount != 0) {
            items(
                cars.itemCount,
                key = { index -> cars[index]?.carId ?: index }
            ) {
                val car = cars[it]
                if (car != null) {
                    Log.d(
                        "MyCarsScreen",
                        "Car: $car, name ${car.car_name}, id ${car.carId}, year ${car.year}, coolant ${car.last_coolant_change} "
                    )
                    if (navController != null) {
                        CardCar(car, navController)
                    }
                }
            }
        } else {
            item {

                Text(
                    text = "No cars added yet",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 32.sp
                )

            }
        }
    }

}

/**
 * Composable function representing a card item displaying car details.
 *
 * @param car The CarModel representing the car data.
 * @param navController The NavController for navigating to the "car_info" destination.
 */
@Composable
fun CardCar(
    car: CarModel,
    navController: NavController,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                val CarSend = car
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "car",
                    CarSend
                )
                navController.navigate("car_info")
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant),

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Text(
                text = car.car_name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant

            )
            Image(
                painter = painterResource(id = R.drawable.car_icon_alone),
                contentDescription = "Car brand",
                modifier = Modifier
                    .wrapContentSize(Alignment.CenterStart)
                    .fillMaxHeight()
                    .size(100.dp)
                    .padding(start = 32.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.CenterEnd)
                    .padding(end = 64.dp)
            ) {
                Text(
                    text = car.brand,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = car.year,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = "Presiona para ver más",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.surfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}