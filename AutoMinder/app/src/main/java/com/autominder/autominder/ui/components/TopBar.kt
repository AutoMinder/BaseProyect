package com.autominder.autominder.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.autominder.autominder.ui.navigation.Destinations

@Composable
fun TopBar(navController: NavHostController, topAppBarState: MutableState<Boolean>) {
    AnimatedVisibility(visible = topAppBarState.value) {
        androidx.compose.material.TopAppBar(
            backgroundColor = MaterialTheme.colorScheme.primary,

            ) {
            BackButtonTopBar(navController)
            Text(
                text = navController.currentBackStackEntryAsState().value?.destination?.route?.let {
                    getTitleByRoute(
                        it
                    )
                }
                    ?: "AutoMinder",
                modifier = Modifier.padding(18.dp, 0.dp, 0.dp, 0.dp),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

}

fun getTitleByRoute(route: String): String {
    return when (route) {
        Destinations.MyCars.route -> "Mis Carros"
        Destinations.PrincipalMenu.route -> "AutoMinder"
        Destinations.UserInfo.route -> "Usuario"
        else -> "AutoMinder"
    }
}

@Composable
fun BackButtonTopBar(navController: NavHostController) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}