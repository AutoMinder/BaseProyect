package com.autominder.autominder.carinfo.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.myCars.data.CarDataModel
import com.autominder.autominder.myCars.ui.MyCarsViewModel


@Composable
fun CarInfoScreen(
    car: CarDataModel,
    viewModel: MyCarsViewModel = viewModel(
        factory = MyCarsViewModel.Factory,
    )
) {

    Text(text = car.name)
}