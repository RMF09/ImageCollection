package com.rmf.imagecollection.ui.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popBackStack
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.ramcosta.composedestinations.utils.isRouteOnBackStack
import com.rmf.imagecollection.R
import com.rmf.imagecollection.presentation.NavGraphs
import com.rmf.imagecollection.presentation.appCurrentDestinationAsState
import com.rmf.imagecollection.presentation.destinations.Destination
import com.rmf.imagecollection.presentation.destinations.FavoriteListScreenDestination
import com.rmf.imagecollection.presentation.destinations.PhotoListScreenDestination
import com.rmf.imagecollection.presentation.startAppDestination

@Composable
fun MyBottomNavigation( navController: NavController
) {

    NavigationBar(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))

    ) {
        Spacer(modifier = Modifier.width(80.dp))
        MenuDestination.values().forEach { destination ->

            val isCurrentDestOnBackStack =
                navController.isRouteOnBackStack(destination.direction)

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                ),
                selected = isCurrentDestOnBackStack,
                onClick = {
                    if (isCurrentDestOnBackStack) {
                        // When we click again on a bottom bar item and it was already selected
                        // we want to pop the back stack until the initial destination of this bottom bar item
                        navController.popBackStack(destination.direction, false)
                        return@NavigationBarItem
                    }

                    navController.navigate(destination.direction) {
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        //if (destination.direction == NavGraphs.home.startRoute)
                        popUpTo(NavGraphs.root) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(text = stringResource(id = destination.label))
                }
            )
        }
        Spacer(modifier = Modifier.width(80.dp))
    }
}

enum class MenuDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
){
    Home(
        direction = PhotoListScreenDestination,
        icon = Icons.Default.Home,
        label = R.string.text_menu_home
    ),
    Favorite(
    direction = FavoriteListScreenDestination,
    icon = Icons.Default.Favorite,
    label = R.string.text_menu_favorite
    )
}