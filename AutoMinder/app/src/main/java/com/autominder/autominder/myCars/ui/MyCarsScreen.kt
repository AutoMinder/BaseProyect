package com.autominder.autominder.myCars.ui

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.autominder.autominder.R
import com.autominder.autominder.carinfo.ui.CarInfoViewModel
import com.autominder.autominder.myCars.data.CarDataModel


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
            if (isLoading) {
                //*
                // This is the loading screen, it will be displayed while the data is being fetched
                // *//

                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .padding(16.dp)
                )

            } else {
                //*
                // This is the main screen, it will be displayed when the data is fetched
                // *//

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
        onClick = { navController.navigate("add_car") },
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
    myCarListState: State<List<CarDataModel>>,
    navController: NavController?,

    ) {

    //* Lazy column to show the different cars (is like the RecyclerView)  *//
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
    car: CarDataModel, navController: NavController,
    infoViewModel: CarInfoViewModel = viewModel(
        factory = CarInfoViewModel.Factory
    )
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()


            //* If clicked, it will navigate to the details of the specific car with the id*//
            .clickable {
                navController.navigate("car_info/${car.id}")
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Text(
                text = car.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Image(

                painter = painterResource(id = R.drawable.ic_car),
                contentDescription = "Car brand",
                modifier = Modifier
                    .wrapContentSize(Alignment.CenterStart)
                    .fillMaxHeight()
                    .size(100.dp)
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.CenterEnd)
                    .padding(end = 32.dp)
            ) {
                Text(text = car.model, fontSize = 22.sp)
                Text(text = car.year, fontSize = 22.sp)
            }
            Text(
                text = "Presiona para ver más",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.onPrimaryContainer),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}