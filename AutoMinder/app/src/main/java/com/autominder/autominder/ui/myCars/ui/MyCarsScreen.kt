package com.autominder.autominder.ui.myCars.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.autominder.autominder.R
import com.autominder.autominder.ui.carinfo.ui.CarInfoViewModel
import com.autominder.autominder.ui.components.LoadingScreen
import com.google.android.material.color.utilities.MaterialDynamicColors.background
import kotlinx.coroutines.launch


//*
// This function recieves the navController and the viewModel for my cars
// *//
@Composable
fun MyCarsScreen(
    navController: NavController,
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory,
    )

) {
    val isLoading by viewModel.isLoading.collectAsState(false)
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    fun handleUiStatus(status: OwnCarsUiStatus) { //This function will handle the status of the screen
        when (status) {
            is OwnCarsUiStatus.Success -> {
                Toast.makeText(context, "Info obtained succesfully!", Toast.LENGTH_SHORT).show()
                println("Success: ${status.cars}")
            }
            is OwnCarsUiStatus.Error -> {
                Toast.makeText(context, "Error en el fetcheo de carros", Toast.LENGTH_SHORT).show()
                Log.d("MyCarsViewModel", "Error: ${status.exception}")
            }
            is OwnCarsUiStatus.ErrorWithMessage -> {
                Toast.makeText(context, "Error con mensaje en el fetcheo de carros", Toast.LENGTH_SHORT).show()
                Log.d("MyCarsViewModel", "ErrorWithMessage: ${status.message}")
            }
            else -> {Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()}
        }
    }

    //*
    // The Scaffold is the one in charge to add the floating button
    // and the content in { } is the one that will be displayed
    // *//
    Scaffold(
        floatingActionButton = { FloatingAddButtonCar(navController) },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.surface),
        ) {
    
            LaunchedEffect(coroutineScope){ //This is a coroutine that will be launched when the screen is created

                //Coroutine that will observe the status of the view model
                coroutineScope.launch {
                    viewModel.status.observe(lifecycleOwner) { status -> //This is the observer of the status
                        handleUiStatus(status) //This function will handle the status
                    }
                }
            }


            //Checks if is loading, if it is, it will display the loading screen, if not, it will display the main screen
            if (isLoading) {
                LoadingScreen()

            } else {
                MainScreenCars(viewModel, navController)

            }
        }
    }
}


//*
//Floating button to add a new car, it will navigate to the add car screen
// *//
@Composable
fun FloatingAddButtonCar(navController: NavController) {
    androidx.compose.material3.FloatingActionButton(
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(Alignment.BottomEnd),
        onClick = { navController.navigate("add_car") },
        containerColor = Color(0xFF006496),
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add button")
    }
}


//*
//This two are the main screen of the cars, it will display all the cars
// It receives the view model and nav controller
// *//
@Composable
fun MainScreenCars(viewModel: MyCarsViewModel, navController: NavController?) {

    //* This val is containing the list of the view model *//
    val myCarListState = viewModel.myCarsList.observeAsState(emptyList())

    //Call to the function that will display the list of cars, it recieves the list and the nav controller
    MyCarSection(myCarListState, navController)
}

@Composable
fun MyCarSection(
    myCarListState: State<List<com.autominder.autominder.data.database.models.CarModel>>,
    navController: NavController?,

    ) {

    //* Lazy column to show the different cars (is like the RecyclerView)  *//
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp)
    )

    {


        //* Check if the list of cars is empty *//
        if (myCarListState.value.isEmpty()) {
            item {
                Text(
                    text = "No cars added yet",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {

            //* Renders the card car *//
            items(myCarListState.value) { car ->
                if (navController != null) {
                    CardCar(car, navController)
                }
            }
        }
    }
}

//* This is the individual carCard for each of the cars*//

@Composable
fun CardCar(
    car: com.autominder.autominder.data.database.models.CarModel, navController: NavController,
    infoViewModel: CarInfoViewModel = viewModel(
        factory = CarInfoViewModel.Factory
    )
) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()


            //* If clicked, it will navigate to the details of the specific car with the id*//
            .clickable {
                val CarSend = car
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    "car",
                    CarSend
                )
                navController.navigate("car_info")
                //infoViewModel.fetchCarMaintenanceInfoByCarId(car.id)
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .width(300.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFFFFFFF))

        ) {

            val gradientColors = listOf(
                Color.Red,
                Color.Magenta,
                Color.Blue,
                Color.Cyan,
                Color.Green,
                Color.Yellow
            )
            Text(
                text = car.car_name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF000000)

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
                Text(text = car.brand, color = Color(0xFF72787E), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(text = car.year, color = Color(0xFF72787E), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            Text(
                text = "Presiona para ver más",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0xFF006496))
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}