package com.autominder.autominder.addcar.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun AddCarScreenPreview() {
    AddCarScreen()
}

@Composable
fun AddCarScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderText()
        AddCarForm()
    }
}

@Composable
fun HeaderText() {
    Text(
        text = "Agregar Automovil",
        fontSize = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top= 32.dp, bottom = 16.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun AddCarForm() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { FieldsWrapper() }
    }
}

@Composable
fun FieldsWrapper() {
    val context = LocalContext.current
    //TODO(): Hacer con dummydata y luego con la API
    val carBrands = arrayOf("Toyota", "Nissan", "Hyundai", "Isuzu", "BMW")
    val carModels = arrayOf("Yaris", "Corolla", "Hyundai", "Isuzu", "BMW")

    CarBrandMenu(context, carBrands)
    CarModelMenu(context, carModels)
    CarYear()
    CarDistance()
    CarLastOilChange()
    CarLastMaintenance()
    SaveCar()
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
                label = { Text("Marca del vehiculo") },
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
                label = { Text("Modelo del vehiculo") },
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
fun CarYear() {
    //TODO():
    val carYears = arrayOf("2021", "2020", "2019", "2018", "2017")

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "Año del vehiculo") },
            placeholder = { Text(text = "Your Placeholder/Hint") },
        )
    }
}

@Composable
fun CarDistance() {
    //TODO(): Hacer con dummydata y luego con la API

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "Distancia recorrida del vehiculo") },
            placeholder = { Text(text = "Your Placeholder/Hint") },
        )
    }
}

@Composable
fun CarLastOilChange() {
    //TODO(): Hacer con dummydata y luego con la API

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "Ultimo cambio de aceite") },
            placeholder = { Text(text = "Your Placeholder/Hint") },
        )
    }
}

@Composable
fun CarLastMaintenance() {
    //TODO(): Hacer con dummydata y luego con la API

    var text by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            label = { Text(text = "Mantenimiento del vehiculo") },
            placeholder = { Text(text = "Your Placeholder/Hint") },
        )
    }
}

@Composable
fun SaveCar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Guardar Automovil")
        }
    }
}