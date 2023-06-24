package com.autominder.autominder.ui.carinfo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.autominder.autominder.data.database.models.CarEntity
import com.autominder.autominder.ui.components.LoadingScreen
import com.autominder.autominder.ui.myCars.ui.MyCarsViewModel

//
// Screen to show the car details
// MyCarsScreen sends the id, and the navigationHost "search" the car
// with the respective id
// *
//
@Composable
fun CarInfoScreen(
    car: CarEntity,
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory,
    ),
    infoViewModel: CarInfoViewModel = viewModel(
        factory = CarInfoViewModel.Factory,
    ),
    navController: NavController
) {
    val isLoading by infoViewModel.isLoading.collectAsState(false)


    Scaffold(
    ) {
        Box(modifier = Modifier.padding(it)) {

            if (isLoading) {
                LoadingScreen()
            } else {
                CarInfoMainScreen(car, navController)
            }
        }
    }
}

@Composable
fun CarInfoMainScreen(car: CarEntity, navController: NavController) {
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
                CarNameHeader(car)
                CarBrand(car)
                CarModel(car)
                CarYearCard(car)
                CarMileage(car)
                CarLastMaintenanceDate(car)
                LastOilChange(car)
                LastCoolantChange(car)
                ConnectObd(navController)
            }
        }
    }
}

@Composable
fun CarNameHeader(car: CarEntity) {
    Text(
        text = car.car_name,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        fontWeight = FontWeight(600),
    )
}

@Composable
fun CarBrand(car: CarEntity) {
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
                text = car.brand?: "No especificado",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun CarModel(car: CarEntity) {
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
                text = car.model?: "No especificado",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
fun CarYearCard(car: CarEntity) {
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
fun CarMileage(carInfo: CarEntity) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Kilometraje",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )

            Text(
                text = carInfo.kilometers.toString(),
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CarLastMaintenanceDate(car: CarEntity) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Último mantenimiento",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )

            Text(
                text = car.last_maintenance,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LastOilChange(car: CarEntity) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Último cambio de aceite",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )

            Text(
                text = car.last_oil_change,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LastCoolantChange(car: CarEntity) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer),
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = "Último cambio de refigerante",
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )

            Text(
                text = car.last_coolant_change,
                fontWeight = FontWeight(600),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ConnectObd(navController: NavController) {
    Button(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),

        onClick = { navController.navigate("obd_sensor") }) {
        Text(
            text = "Conectar OBD",
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
