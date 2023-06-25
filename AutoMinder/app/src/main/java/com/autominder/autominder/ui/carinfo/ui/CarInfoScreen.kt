package com.autominder.autominder.ui.carinfo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.autominder.autominder.R
import com.autominder.autominder.data.database.models.CarModel
import com.autominder.autominder.ui.components.LoadingScreen
import com.autominder.autominder.ui.myCars.data.myCarsdummy
import com.autominder.autominder.ui.myCars.ui.MyCarsViewModel

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
fun CarInfoMainScreen(car: CarModel, navController: NavController) {
    Card(
        modifier = Modifier.padding(0.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
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
fun CarNameHeader(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color(0xFF006496)),
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
                text = car.car_name,
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF)
            )
            Image(
                painter = painterResource(id = R.drawable.editar2),
                contentDescription = "Editar nombre",
                modifier = Modifier
                    .padding(start = 24.dp)
                    .wrapContentSize(Alignment.CenterStart)
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun CarBrand(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color(0xFF006496)),
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
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF)
            )

            Text(
                text = car.brand,
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF)
            )
        }
    }
}

@Composable
fun CarModel(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color(0xFF006496)),
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
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF)
            )

            Text(
                text = car.model,
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF)
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
        colors = CardDefaults.cardColors(Color(0xFF006496))
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
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF)
            )

            Text(
                text = car.year.toString(),
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF)
            )
        }
    }
}

@Composable
fun CarMileage(carInfo: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color(0xFF006496)),
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
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row() {
                Text(
                    text = carInfo.kilometers.toString(),
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(id = R.drawable.editar2),
                    contentDescription = "Editar fecha de cambio de kilometraje",
                    modifier = Modifier
                        .padding(start = 96.dp)
                        .wrapContentSize(Alignment.CenterStart)
                        .size(20.dp)
                )
            }


        }
    }
}

@Composable
fun CarLastMaintenanceDate(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color(0xFF006496)),
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
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row() {
                Text(
                    text = car.last_maintenance!!,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(id = R.drawable.editar2),
                    contentDescription = "Editar fecha de ultimo mantenimiento",
                    modifier = Modifier
                        .padding(start = 64.dp)
                        .wrapContentSize(Alignment.CenterStart)
                        .size(20.dp)
                )
            }

        }
    }
}

@Composable
fun LastOilChange(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color(0xFF006496)),
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
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row() {
                Text(
                    text = car.last_oil_change!!,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.drawable.editar2),
                    contentDescription = "Editar fecha de cambio de aceite",
                    modifier = Modifier
                        .padding(start = 64.dp)
                        .wrapContentSize(Alignment.CenterStart)
                        .size(20.dp)
                )
            }


        }
    }
}

@Composable
fun LastCoolantChange(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(Color(0xFF006496)),
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
                fontSize = 18.sp,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row() {
                Text(
                    text = car.last_coolant_change!!,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.drawable.editar2),
                    contentDescription = "Editar fecha de cambio de refrigerante",
                    modifier = Modifier
                        .padding(start = 64.dp)
                        .wrapContentSize(Alignment.CenterStart)
                        .size(20.dp)
                )
            }


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
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        onClick = { navController.navigate("obd_sensor") }) {
        Text(
            text = "Conectar OBD",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF)
        )
    }
}
