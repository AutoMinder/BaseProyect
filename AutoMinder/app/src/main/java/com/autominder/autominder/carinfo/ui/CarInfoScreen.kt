package com.autominder.autominder.carinfo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.components.LoadingScreen
import com.autominder.autominder.models.CarModel
import com.autominder.autominder.myCars.ui.MyCarsViewModel

//
// Screen to show the car details
// MyCarsScreen sends the id, and the navigationHost "search" the car
// with the respective id
// *
//
@Composable
fun CarInfoScreen(
    car: CarModel,
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory,
    ),
    infoViewModel: CarInfoViewModel = viewModel(
        factory = CarInfoViewModel.Factory,
    )


) {
    val isLoading by infoViewModel.isLoading.collectAsState(false)
    LaunchedEffect(key1 = car.id) {
        infoViewModel.fetchCarMaintenanceInfoByCarId(car.id)
    }

    Scaffold(
        bottomBar = {
            //TODO: Call the BottomNavigationForCarInfo
        },
    ) {
        Box(modifier = Modifier.padding(it)) {

            if (isLoading) {
                LoadingScreen()
            } else {
                CarInfoMainScreen(car)
            }
        }
    }
}

@Composable
fun CarInfoMainScreen(car: CarModel) {
    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            item {

                //Calling all the cards of individual information
                CarNameHeader(car)
                CarBrand(car)
                CarModel(car)
                CarYearCard(car)
                CarMileage(car)
                ConnectObd()
            }
        }
    }
}

@Composable
fun CarNameHeader(car: CarModel) {
    Text(
        text = car.name,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        fontWeight = FontWeight(600),
    )
}

@Composable
fun CarBrand(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.small

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Text(
                text = "Marca",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = car.brand,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun CarModel(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Text(
                text = "Modelo",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = car.model,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun CarYearCard(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Text(
                text = "Año",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = car.year.toString(),
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun CarMileage(carInfo: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Text(
                text = "Kilometraje",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = carInfo.kilometers.toString(),
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun ConnectObd(){
    Button(onClick = { /*TODO*/ }) {

    }
}