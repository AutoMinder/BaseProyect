package com.autominder.autominder.components

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.autominder.autominder.navigation.Destinations
import com.autominder.autominder.ui.theme.AutoMinderTheme
import com.autominder.autominder.ui.theme.md_theme_light_background
import com.autominder.autominder.ui.theme.md_theme_light_secondaryContainer
import com.autominder.autominder.ui.theme.md_theme_light_tertiaryContainer

@Composable
fun BottomNavigationBar(navHostController: NavHostController, items: List<Destinations>) {
    val currentRoute = currentRoute(navHostController)

    BottomNavigation(
        backgroundColor = md_theme_light_tertiaryContainer, /*TODO Fix the color of this*/

        )
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