package com.autominder.autominder.addcar.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.addcar.data.CarModel

@Composable
fun AddCarScreen(viewModel: AddCarViewModel = viewModel(factory = AddCarViewModel.Factory)) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderText()
        AddCarForm(viewModel)
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
fun AddCarForm(viewModel: AddCarViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { FieldsWrapper(viewModel) }
    }
}

@Composable
fun FieldsWrapper(viewModel: AddCarViewModel) {
    val context = LocalContext.current
    //TODO(): Hacer con dummydata y luego con la API
    val carBrands = arrayOf("Toyota", "Nissan", "Hyundai", "Isuzu", "BMW")
    val carModels = arrayOf("Yaris", "Corolla", "Hyundai", "Isuzu", "BMW")

    //viewModel values
    val profileCarName: String by viewModel.profileCarName.observeAsState(initial = "")
    val carYear: String by viewModel.carYear.observeAsState(initial = "")
    val carKilometers: String by viewModel.carKilometers.observeAsState("")
    val carLastOilChange: String by viewModel.carLastOilChange.observeAsState(initial = "")
    val carLastMaintenance: String by viewModel.carLastMaintenance.observeAsState(initial = "")
    val newCar: CarModel by viewModel.newCar.observeAsState(initial = CarModel("", "", "", "", ""))
    val addCarEnable: Boolean by viewModel.addCarEnable.observeAsState(initial = false)

    CarName(profileCarName) {
        viewModel.onAddCarChange(
            it,
            carYear,
            carKilometers,
            carLastOilChange,
            carLastMaintenance
        )
    }
    CarBrandMenu(context, carBrands)
    CarModelMenu(context, carModels)
    CarYear(carYear) {
        viewModel.onAddCarChange(
            profileCarName,
            it,
            carKilometers,
            carLastOilChange,
            carLastMaintenance
        )
    }
    CarDistance(carKilometers) {
        viewModel.onAddCarChange(profileCarName, carYear, it, carLastOilChange, carLastMaintenance)
    }
    CarLastOilChange(carLastOilChange) {
        viewModel.onAddCarChange(profileCarName, carYear, carKilometers, it, carLastMaintenance)
    }
    CarLastMaintenance(carLastMaintenance) {
        viewModel.onAddCarChange(profileCarName, carYear, carKilometers, carLastOilChange, it)
    }
    SaveCar(addCarEnable) { viewModel.addCar(newCar) }
}

@Composable
fun CarName(profileCarName: String, onAddCarChange: (String) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = profileCarName,
            onValueChange = {
                onAddCarChange(it)
            },
            label = { Text(text = "Nombre del perfil del automovil") }
        )
    }
}

@Composable
fun CarBrandMenu(context: Context, carBrands: Array<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(carBrands[0]) }

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
            TextField(
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
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarModelMenu(context: Context, carModels: Array<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(carModels[0]) }

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
            TextField(
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
        TextField(
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
        TextField(
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = carLastOilChange,
            onValueChange = {
                onAddCarChange(it)
            },
            label = { Text(text = "Ultimo cambio de aceite") },
            placeholder = { Text(text = "dd-mm-aaaa") },
        )
    }
}

@Composable
fun CarLastMaintenance(carLastMaintenance: String, onAddCarChange: (String) -> Unit) {
    //TODO(): Hacer con dummydata y luego con la API

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = carLastMaintenance,
            onValueChange = {
                onAddCarChange(it)
            },
            label = { Text(text = "Ultimo mantenimiento realizado") },
            placeholder = { Text(text = "dd-mm-aaaa") },
        )
    }
}

@Composable
fun SaveCar(addCarEnable: Boolean ,addCar: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { addCar() }, enabled = addCarEnable) {

            Text(text = "Guardar Automovil")
        }
    }
}