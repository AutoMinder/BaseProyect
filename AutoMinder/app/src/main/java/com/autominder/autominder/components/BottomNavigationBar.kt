package com.autominder.autominder.components

import android.transition.Fade
import androidx.activity.compose.LocalActivityResultRegistryOwner.current
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.border
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.autominder.autominder.navigation.Destinations



//*
// This composable displays the bottom navigation bar
//
// It receives the navHostController,
// the items to be displayed and the state of the bottom bar (to show or hide it depending on the route)
//
// *//


@Composable
fun BottomNavigationBar(
    navHostController: NavHostController,
    items: List<Destinations>,
    bottomBarState: MutableState<Boolean>,
) {

    //*
    // This variable is used to know the current route
    // *//

    val currentRoute = currentRoute(navHostController)
    val density = LocalDensity.current

    //*
    // Using AnimatedVisibility to show or hide the bottom bar, otherwise it will be always visible
    // *//
    AnimatedVisibility(
        visible = bottomBarState.value,
        content = {
            BottomNavigation(
                backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                modifier = Modifier.border(
                    0.1.dp,
                    androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                ),
            )
            //*
            // items.forEach is used to display the items of the bottom bar
            // *//
            {
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        },
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
        })
}