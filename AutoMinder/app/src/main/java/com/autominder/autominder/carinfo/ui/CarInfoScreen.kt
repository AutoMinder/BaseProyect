package com.autominder.autominder.carinfo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.carinfo.data.CarMaintenanceData
import com.autominder.autominder.myCars.data.CarDataModel
import com.autominder.autominder.myCars.data.myCarsdummy
import com.autominder.autominder.myCars.ui.MyCarsViewModel

//
// Screen to show the car details
// MyCarsScreen sends the id, and the navigationHost "search" the car
// with the respective id
// *
//
@Composable
fun CarInfoScreen(
    car: CarDataModel,
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory,
    ),
    infoViewModel: CarInfoViewModel = viewModel(
        factory = CarInfoViewModel.Factory,
    )
) {
    val carInfoStateList = infoViewModel.carInfoList.observeAsState(emptyList())
    val carInfo = carInfoStateList.value.find { it.carId == car.id }
    infoViewModel.fetchCarMaintenanceInfoByCarId(car.id)

    Scaffold(
        bottomBar = {
            //TODO: Call the BottomNavigationForCarInfo
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            CarInfoMainScreen(car, carInfo)
        }
    }
}

@Composable
fun CarInfoMainScreen(car: CarDataModel, carInfo: CarMaintenanceData?) {
    Card(
        modifier = Modifier.padding(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
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
                CarMileage(carInfo)
            }
        }
    }
}

@Composable
fun CarNameHeader(car: CarDataModel) {
    Text(
        text = car.name,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        fontWeight = FontWeight(600),
    )
}

@Composable
fun CarBrand(car: CarDataModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(280.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
fun CarModel(car: CarDataModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(280.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
fun CarYearCard(car: CarDataModel) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(280.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
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
                text = car.year,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun CarMileage(carInfo: CarMaintenanceData?) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .width(280.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Text(
                text = "Millaje",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Text(
                text = carInfo?.mileage.toString(),
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
@Preview
fun preview() {
    CarBrand(car = myCarsdummy[0])
}