package com.autominder.autominder.addcar.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.autominder.autominder.addcar.data.CarModel
import java.text.SimpleDateFormat

@Composable
fun AddCarScreen(
    viewModel: AddCarViewModel = viewModel(factory = AddCarViewModel.Factory),
    navController: NavController
) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderText()
        AddCarForm(viewModel, navController)
    }
}

@Composable
fun HeaderText() {
    Text(
        text = "Agregar Automovil",
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 16.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun AddCarForm(viewModel: AddCarViewModel, navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { FieldsWrapper(viewModel, navController) }
    }
}

@Composable
fun FieldsWrapper(viewModel: AddCarViewModel, navController: NavController) {
    val context = LocalContext.current
    //TODO(): Hacer con la API
    val carBrandList by remember { viewModel.carBrandsList }.collectAsState()
    val carModelList by remember { viewModel.carModelsList }.collectAsState()

    //viewModel values
    val profileCarName: String by viewModel.profileCarName.collectAsState(initial = "")
    val carBrand: String by viewModel.carBrand.collectAsState(initial = "")
    val carModel: String by viewModel.carModel.collectAsState(initial = "")
    val carYear: String by viewModel.carYear.collectAsState(initial = "")
    val carKilometers: String by viewModel.carKilometers.collectAsState("")
    val carLastOilChange: String by viewModel.carLastOilChange.collectAsState(initial = "")
    val carLastMaintenance: String by viewModel.carLastMaintenance.collectAsState(initial = "")
    val newCar: CarModel by viewModel.newCar.collectAsState(
        initial = CarModel(
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
    )
    val addCarEnable: Boolean by viewModel.addCarEnable.collectAsState(initial = false)

    CarName(profileCarName) {
        viewModel.onAddCarChange(
            it,
            carBrand,
            carModel,
            carYear,
            carKilometers,
            carLastOilChange,
            carLastMaintenance
        )
    }
    CarBrandMenu(context, carBrandList, carBrand) {
        viewModel.onAddCarChange(
            profileCarName,
            it,
            carModel,
            carYear,
            carKilometers,
            carLastOilChange,
            carLastMaintenance
        )
    }
    CarModelMenu(context, carModelList, carModel) {
        viewModel.onAddCarChange(
            profileCarName,
            carBrand,
            it,
            carYear,
            carKilometers,
            carLastOilChange,
            carLastMaintenance
        )
    }
    CarYear(carYear) {
        viewModel.onAddCarChange(
            profileCarName,
            carBrand,
            carModel,
            it,
            carKilometers,
            carLastOilChange,
            carLastMaintenance
        )
    }
    CarDistance(carKilometers) {
        viewModel.onAddCarChange(
            profileCarName, carBrand,
            carModel, carYear, it, carLastOilChange, carLastMaintenance
        )
    }
    CarLastOilChange(carLastOilChange) {
        viewModel.onAddCarChange(
            profileCarName, carBrand,
            carModel, carYear, carKilometers, it, carLastMaintenance
        )
    }
    CarLastMaintenance(carLastMaintenance) {
        viewModel.onAddCarChange(
            profileCarName, carBrand,
            carModel, carYear, carKilometers, carLastOilChange, it
        )
    }
    SaveCar(addCarEnable, navController) { viewModel.addCar(newCar) }
}

@Composable
fun CarName(profileCarName: String, onAddCarChange: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = profileCarName,
            onValueChange = {
                onAddCarChange(it)
            },
            label = { Text(text = "Nombre del perfil del automovil") }
        )
    }
}

@Composable
fun CarBrandMenu(
    context: Context,
    carBrands: List<String>,
    carBrand: String,
    onAddCarChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(carBrand) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                label = { Text("Marca del automovil") },
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                carBrands.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            onAddCarChange(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarModelMenu(
    context: Context,
    carModels: List<String>,
    carModel: String,
    onAddCarChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(carModel) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                label = { Text("Modelo del automovil") },
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                carModels.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            onAddCarChange(item)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarYear(carYear: String, onAddCarChange: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = carYear,
            onValueChange = {
                onAddCarChange(it)
            },
            label = { Text(text = "Año del automovil") }
        )
    }
}

@Composable
fun CarDistance(carKilometers: String, onAddCarChange: (String) -> Unit) {
    //TODO(): Hacer con dummydata y luego con la API

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = carKilometers,
            onValueChange = {
                onAddCarChange(it)
            },
            label = { Text(text = "Distancia recorrida del automovil") },
            placeholder = { Text(text = "Ingresar en km") },
        )
    }
}


@Composable
fun CarLastOilChange(carLastOilChange: String, onAddCarChange: (String) -> Unit) {
    //TODO(): Hacer con dummydata y luego con la API
    val openDialog = remember { mutableStateOf(false) }
    val date = remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        date.value = SimpleDateFormat("dd/MM/yyyy").format(datePickerState.selectedDateMillis).toString()

                    },
                ) {
                    Text("OK")

                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                )
        }

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { openDialog.value = true },
        contentAlignment = Alignment.Center,

        ) {
        OutlinedTextField(
            value = date.value,
            onValueChange = {
                onAddCarChange(it)
            },

            label = { Text(text = "Ultimo cambio de aceite") },
            modifier = Modifier
                .pointerInput(Unit) {}
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { openDialog.value = true },
            readOnly = true,
            enabled = false,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = com.google.android.material.R.drawable.material_ic_calendar_black_24dp),
                    contentDescription = ""
                )
            }
        )


    }
}

@Composable
fun CarLastMaintenance(carLastMaintenance: String, onAddCarChange: (String) -> Unit) {
    //TODO(): Hacer con dummydata y luego con la API

    val openDialog = remember { mutableStateOf(false) }
    val date = remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        date.value = SimpleDateFormat("dd/MM/yyyy").format(datePickerState.selectedDateMillis).toString()

                    },
                ) {
                    Text("OK")

                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
            )
        }

    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { openDialog.value = true },
        contentAlignment = Alignment.Center,

        ) {
        OutlinedTextField(
            value = date.value,
            onValueChange = {
                onAddCarChange(it)
            },

            label = { Text(text = "Ultimo mantenimiendo") },
            modifier = Modifier
                .pointerInput(Unit) {}
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { openDialog.value = true },
            readOnly = true,
            enabled = false,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = com.google.android.material.R.drawable.material_ic_calendar_black_24dp),
                    contentDescription = ""
                )
            }
        )


    }
}

@Composable
fun SaveCar(addCarEnable: Boolean, navController: NavController, addCar: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            addCar()
            navController.navigateUp()
        }, enabled = addCarEnable) {

            Text(text = "Guardar Automovil")
        }
    }
}