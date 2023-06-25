package com.autominder.autominder.ui.addcar.ui

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
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
    val carBrandList by remember { viewModel.carBrandsList }.collectAsState()
    val carModelList by remember { viewModel.carModelsList }.collectAsState()

    //viewModel values
    val profileCarName: String by viewModel.profileCarName.collectAsState(initial = "")
    val carBrand: String by viewModel.carBrand.collectAsState(initial = "")
    val carModel: String by viewModel.carModel.collectAsState(initial = "")
    val carYear: String by viewModel.carYear.collectAsState(initial = "")
    val carKilometers: String by viewModel.carKilometers.collectAsState(initial = "")
    val carLastOilChange: String by viewModel.carLastOilChange.collectAsState("")
    val carLastMaintenance: String by viewModel.carLastMaintenance.collectAsState("")
    val carLastCoolantDate: String by viewModel.carLastCoolantDate.collectAsState("")
    val addCarEnable: Boolean by viewModel.addCarEnable.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()


    CarName(profileCarName) {
        viewModel.onAddCarChange(
            it,
            carBrand,
            carModel,
            carYear,
            carKilometers,
            carLastOilChange,
            carLastMaintenance,
            carLastCoolantDate
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
            carLastMaintenance,
            carLastCoolantDate
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
            carLastMaintenance,
            carLastCoolantDate
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
            carLastMaintenance,
            carLastCoolantDate
        )
    }
    CarDistance(carKilometers) {
        viewModel.onAddCarChange(
            profileCarName, carBrand,
            carModel, carYear, it, carLastOilChange, carLastMaintenance, carLastCoolantDate
        )
    }
    CarLastMaintenance(carLastMaintenance) {
        viewModel.onAddCarChange(
            profileCarName, carBrand,
            carModel, carYear, carKilometers, it, carLastOilChange, carLastCoolantDate
        )
    }
    CarLastOilChange(carLastOilChange) {
        viewModel.onAddCarChange(
            profileCarName, carBrand,
            carModel, carYear, carKilometers, carLastMaintenance, it, carLastCoolantDate
        )
    }

    CarLastCoolant(carLastCoolantDate) {
        viewModel.onAddCarChange(
            profileCarName, carBrand,
            carModel, carYear, carKilometers, carLastMaintenance, carLastOilChange, it
        )
    }

    Button(

        enabled = addCarEnable,
        onClick = {
            coroutineScope.launch {
                viewModel.addCarToDatabase(
                    name = profileCarName,
                    model = carModel,
                    brand = carBrand,
                    year = carYear,
                    kilometers = carKilometers,
                    kilometersDate = null,
                    lastMaintenance = carLastMaintenance,
                    lastOilChange = carLastOilChange,
                    lastCoolantChange = carLastCoolantDate,
                    mayorTuning = null,
                    minorTuning = null,
                    errorRecord = null,
                )
            }
            navController.popBackStack()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Agregar Automovil")
    }
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
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            shape = MaterialTheme.shapes.small,
            value = profileCarName,
            singleLine = true,
            onValueChange = {
                onAddCarChange(it)
            },
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            label = { Text(text = "Nombre del perfil del automovil") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
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
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.small,
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
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
                colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = MaterialTheme.shapes.small,
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
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            shape = MaterialTheme.shapes.small,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = carYear,
            onValueChange = { newValue ->
                val numericValue = newValue.filter { it.isDigit() }
                onAddCarChange(numericValue)
            },
            label = { Text(text = "Año del automovil") }
        )
    }
}

@Composable
fun CarDistance(carKilometers: String, onAddCarChange: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(

            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = MaterialTheme.shapes.small,
            value = carKilometers,
            onValueChange = { newValue ->
                val numericValue = newValue.filter { it.isDigit() }
                onAddCarChange(numericValue)
            },
            label = { Text(text = "Distancia recorrida del automovil") },
            placeholder = { Text(text = "Ingresar en km") },
        )
    }
}


@Composable
fun CarLastMaintenance(carLastMaintenance: String, onAddCarChange: (String) -> Unit) {

    val openDialog = remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(carLastMaintenance) }
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        date =
                            SimpleDateFormat("yyyy-MM-dd").format(datePickerState.selectedDateMillis)
                                .toString()

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
    LaunchedEffect(date) {
        onAddCarChange(date)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { openDialog.value = true },
        contentAlignment = Alignment.Center,

        ) {
        OutlinedTextField(

            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = MaterialTheme.shapes.small,
            value = date,
            onValueChange = {},
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
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
fun CarLastOilChange(carLastOilChange: String, onAddCarChange: (String) -> Unit) {
    val openDialog = remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(carLastOilChange) }
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        date =
                            SimpleDateFormat("yyyy-MM-dd").format(datePickerState.selectedDateMillis)
                                .toString()
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

    LaunchedEffect(date) {
        onAddCarChange(date)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { openDialog.value = true },
        contentAlignment = Alignment.Center,

        ) {
        OutlinedTextField(
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = MaterialTheme.shapes.small,
            value = date,
            onValueChange = {},
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
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
fun CarLastCoolant(carLastCoolant: String, onAddCarChange: (String) -> Unit) {

    val openDialog = remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(carLastCoolant) }
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())

    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false

            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        date =
                            SimpleDateFormat("yyyy-MM-dd").format(datePickerState.selectedDateMillis)
                                .toString()
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

    LaunchedEffect(date) {
        onAddCarChange(date)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { openDialog.value = true },
        contentAlignment = Alignment.Center,

        ) {
        OutlinedTextField(
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = MaterialTheme.shapes.small,
            value = date,
            onValueChange = {},
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
            label = { Text(text = "Ultimo cambio de refrigerante") },
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