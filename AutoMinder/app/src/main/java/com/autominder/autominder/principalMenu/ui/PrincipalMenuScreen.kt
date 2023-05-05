package com.autominder.autominder.principalMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.autominder.autominder.principalMenu.data.Alerts
import com.autominder.autominder.principalMenu.ui.PrincipalMenuViewModel

@Composable
@Preview(showBackground = true)
fun PrincipalMenuScreen() {
    val viewModel = PrincipalMenuViewModel()

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
    val alerts: Alerts by viewModel.alerts


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column() {
            AutominderHeader()
            AlertsSection()
        }
    }
}

@Composable
fun AutominderHeader() {
    Text(
        text = "Autominder",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )
}

/*
* TODO: Add the "fetch" of the alerts from the database
*  */
@Composable
fun AlertsSection(alerts: Alerts) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            AlertCard()
        }
    }
}

@Composable
fun AlertCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(100.dp)
    ) {

    }
}