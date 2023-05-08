package com.autominder.autominder.principalMenu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
<<<<<<< HEAD
=======
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
>>>>>>> 65bc1b351d9cd205b4382a7d8f6cae33b24a35b7
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.principalMenu.data.Alerts

@Composable
@Preview(showBackground = true)
fun PrincipalMenuScreen(
    viewModel: PrincipalMenuViewModel = viewModel(
        factory = PrincipalMenuViewModel.Factory, //Creates the factory for the view model
    )
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        PrincipalMenu(viewModel)
    }
}

@Composable
fun PrincipalMenu(viewModel: PrincipalMenuViewModel) {
    val alertListState = viewModel.alertsList.observeAsState(emptyList())
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column() {
            AlertsSection(alertListState)
        }
    }
}


/*
* TODO: Add the "fetch" of the alerts from the database
*  */
@Composable
fun AlertsSection(alerts: State<List<Alerts>>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (alerts.value.isEmpty()) {
            item {
                Text(
                    text = "No alerts found",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    )
            }
        }
        items(alerts.value) { alert ->
            AlertCard(alert)
        }
    }
}

@Composable
fun AlertCard(alert: Alerts) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

    ) {
        Text(text = alert.alertName)
    }
}