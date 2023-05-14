package com.autominder.autominder.myCars.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.autominder.autominder.myCars.data.CarDataModel

@Composable
fun MyCarsScreen(
    navController: NavController,
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory,
    )

) {
    Scaffold(
        floatingActionButton = { FloatingAddButtonCar(navController) },
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            MainScreenCars(viewModel, navController)
        }
    }
}

@Composable
fun FloatingAddButtonCar(navController: NavController) {
    androidx.compose.material3.FloatingActionButton(
        onClick = { navController.navigate("add_car") },
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add button")
    }
}

@Composable
fun MainScreenCars(viewModel: MyCarsViewModel, navController: NavController?) {
    val myCarListState = viewModel.myCarsList.observeAsState(emptyList())
    MyCarSection(myCarListState, navController)
}

@Composable
fun MyCarSection(myCarListState: State<List<CarDataModel>>, navController: NavController?) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        if (myCarListState.value.isEmpty()) {
            item {
                Text(
                    text = "No cars added yet",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            items(myCarListState.value) { car ->
                CardCar(car, navController)
            }
        }
    }
}

@Composable
fun CardCar(car: CarDataModel, navController: NavController?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        onClick = { navController?.navigate("car_info") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(150.dp)
        ) {
            Text(
                text = car.name,
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
                Text(text = car.model)
                Text(text = car.year)
            }
        }
    }
}