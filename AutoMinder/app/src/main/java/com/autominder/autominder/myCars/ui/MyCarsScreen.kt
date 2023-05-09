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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.myCars.data.CarDataModel
import com.autominder.autominder.principalMenu.ui.PrincipalMenuViewModel

@Composable
@Preview
fun MyCarsScreen(
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory, //Creates the factory for the view model
    )
) {
    Scaffold(
        floatingActionButton = { FloatingAddButtonCar() },
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            MainScreenCars(viewModel)
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
fun MainScreenCars(viewModel: MyCarsViewModel) {
    val myCarListState = viewModel.myCarsList.observeAsState(emptyList())
    MyCarSection(myCarListState)
}

@Composable
fun MyCarSection(myCarListState: State<List<CarDataModel>>) {
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
                CardCar(car)
            }
        }
    }
}

@Composable
fun CardCar(car: CarDataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(22.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
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