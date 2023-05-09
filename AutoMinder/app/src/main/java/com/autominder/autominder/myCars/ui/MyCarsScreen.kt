package com.autominder.autominder.myCars.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun MyCarsScreen() {
    Scaffold(
        floatingActionButton = { FloatingAddButtonCar() },
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            MainScreenCars()
        }
    }
}

@Composable
fun FloatingAddButtonCar() {
    androidx.compose.material3.FloatingActionButton(
        onClick = { /*TODO*/ },
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add button")
    }
}

@Composable
fun MainScreenCars() {
    CardCar()
}

@Composable
fun CardCar() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp), colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(150.dp)
        ) {
            Text(
                text = "Corolla de usuario",
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                textAlign = TextAlign.Center
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.CenterEnd)
                    .padding(end = 32.dp)
            ) {
                Text(text = "Car Model")
                Text(text = "Car Year")
            }
        }
    }
}