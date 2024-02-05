package com.trainer.srb.rus

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavBar(
    destinations: List<MainScreenDestination>,
    currentDestination: MainScreenDestination,
    onDestinationChanged: (MainScreenDestination) -> Unit
) {
    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = destination == currentDestination,
                icon = {
                    Icon(
                        imageVector = if (destination == currentDestination) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        },
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = destination.text,
                    )
                },
                onClick = {
                    onDestinationChanged(destination)
                }
            )
        }
    }
}