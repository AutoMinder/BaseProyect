package com.autominder.autominder.components

import androidx.compose.foundation.border
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.autominder.autominder.navigation.Destinations

@Composable
fun BottomNavigationBar(navHostController: NavHostController, items: List<Destinations>) {
    val currentRoute = currentRoute(navHostController)

    BottomNavigation(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
        modifier = Modifier.border(0.1.dp, androidx.compose.material3.MaterialTheme.colorScheme.onSurface))
    {
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navHostController.navigate(screen.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
private fun currentRoute(navHostController: NavHostController): String? {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}