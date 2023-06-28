package com.autominder.autominder.ui.carinfo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.autominder.autominder.data.database.models.CarModel
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
    infoViewModel.setNameInfo(car.car_name)


    Scaffold(
    ) {
        Box(modifier = Modifier.padding(it)) {

            if (isLoading) {
                LoadingScreen()
            } else {
                CarInfoMainScreen(car, navController, infoViewModel)
            }
        }
    }
}

@Composable
fun CarInfoMainScreen(
    car: CarModel,
    navController: NavController,
    infoViewModel: CarInfoViewModel
) {
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
                CarNameHeader(car, infoViewModel)
                CarBrand(car)
                CarModel(car)
                CarYearCard(car)
                CarMileage(car, infoViewModel)

                CarLastMaintenanceDate(car, infoViewModel)
                LastOilChange(car, infoViewModel)
                LastCoolantChange(car, infoViewModel)
                ConnectObd(navController)
            }
        }
    }
}

@Composable
fun CarNameHeader(car: CarModel, infoViewModel: CarInfoViewModel) {
    val openDialog = remember { mutableStateOf(false) }
    val name = infoViewModel.updatedCarName.collectAsState()
    val isChanged = infoViewModel.isChangedName.collectAsState()

    if (!isChanged.value) {
        infoViewModel.setNameInfo(car.car_name)
    }


    val normalName = infoViewModel.carName.collectAsState()

    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false },
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(500.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Editar nombre",
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    singleLine = true,
                    value = name.value,
                    onValueChange = { infoViewModel.setUpdatedCarName(it) },
                    label = { Text(text = "Nombre del auto") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),

                    )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {

                    infoViewModel.setCarUpdatedToName(name.value)
                    openDialog.value = false
                    infoViewModel.setChanged(true)
                }) {
                    Text(text = "Guardar")
                }
            }

        }
    }


    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
        shape = MaterialTheme.shapes.small,
        onClick = { openDialog.value = true }

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        )
        {
            Text(
                text = normalName.value,
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar nombre",
                tint = MaterialTheme.colorScheme.onSecondary,
            )

        }
    }
}


@Composable
fun CarBrand(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
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
                color = MaterialTheme.colorScheme.onSecondary
            )

            Text(
                text = car.brand,
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun CarModel(car: CarModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
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
                color = MaterialTheme.colorScheme.onSecondary
            )

            Text(
                text = car.model,
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondary
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
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
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
                color = MaterialTheme.colorScheme.onSecondary
            )

            Text(
                text = car.year.toString(),
                fontWeight = FontWeight(600),
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@Composable
fun CarMileage(carInfo: CarModel, infoViewModel: CarInfoViewModel) {

    val openDialog = remember { mutableStateOf(false) }
    val name = infoViewModel.mileageUpdated.collectAsState()
    val isChanged = infoViewModel.isChangedMileage.collectAsState()

    if (!isChanged.value) {
        infoViewModel.setMileageInfo(carInfo.kilometers)
    }

    val normalName = infoViewModel.mileage.collectAsState()


    if (openDialog.value) {
        Dialog(
            onDismissRequest = { openDialog.value = false },
        ) {
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(500.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Editar kilometraje",
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    singleLine = true,
                    value = name.value,
                    onValueChange = { infoViewModel.setUpdatedMileage(it) },
                    label = { Text(text = "Kilometraje") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                    colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),

                    )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {

                    infoViewModel.setMileageUpdated(name.value)
                    openDialog.value = false
                    infoViewModel.setChangedMileage(true)
                }) {
                    Text(text = "Guardar")
                }
            }

        }
    }


    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
        shape = MaterialTheme.shapes.small,
        onClick = { openDialog.value = true }
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
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = normalName.value,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar nombre",
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            }


        }
    }
}

@Composable
fun CarLastMaintenanceDate(car: CarModel, infoViewModel: CarInfoViewModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
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
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = car.last_maintenance!!,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar nombre",
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            }

        }
    }
}

@Composable
fun LastOilChange(car: CarModel, infoViewModel: CarInfoViewModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
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
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = car.last_oil_change!!,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar nombre",
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
            }


        }
    }
}

@Composable
fun LastCoolantChange(car: CarModel, infoViewModel: CarInfoViewModel) {
    Card(
        modifier = Modifier
            .padding(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
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
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = car.last_coolant_change!!,
                    fontWeight = FontWeight(600),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar nombre",
                    tint = MaterialTheme.colorScheme.onSecondary,
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
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        onClick = { navController.navigate("obd_sensor") }) {
        Text(
            text = "Conectar OBD",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
