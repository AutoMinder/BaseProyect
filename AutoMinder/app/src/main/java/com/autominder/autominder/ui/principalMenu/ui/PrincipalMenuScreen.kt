package com.autominder.autominder.ui.principalMenu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.ui.components.LoadingScreen
import com.autominder.autominder.ui.principalMenu.data.Alerts
import com.autominder.autominder.ui.theme.md_theme_light_error
import kotlinx.coroutines.delay

@Composable
fun PrincipalMenuScreen(
    viewModel: PrincipalMenuViewModel = viewModel(
        factory = PrincipalMenuViewModel.Factory, //Creates the factory for the view model
    )
) {
    val isLoading by viewModel.isLoading.collectAsState(false)
    Box(
        Modifier
            .fillMaxSize()

    ) {
        if (isLoading) {
            LoadingScreen()
        } else {
            PrincipalMenu(viewModel)
        }

    }
}

@Composable
fun PrincipalMenu(viewModel: PrincipalMenuViewModel) {
    val alertListState = viewModel.alertsList.collectAsState(emptyList())


    //TODO: Add the loading screen

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
                    text = "NO HAY ALERTAS",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
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
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor =
                when (alert.alertType) {
                    "SEGURIDAD" -> Color(0xFF006496)
                    "RECOMENDACION" -> Color(0xFF4CAF50)
                    "PRECAUCION" -> Color(0xFF03A9F4)
                    else -> Color(0xFF006496)
        }),

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize(Alignment.Center)
                    .padding(16.dp)
            ) {
                Text(
                    text = alert.alertName,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,

                )
                Text(
                    text = alert.alertDescription,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Justify,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.White,

                    )
                Text(
                    text = alert.alertType,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White,

                    )

            }

        }

    }
}

@Composable
@Preview(showBackground = true)
fun AlertCardPreview() {
    AlertCard(
        Alerts(
            "Alert 1",
            "This is the description of the alert",
            "This is the action to be taken",
        )
    )
}