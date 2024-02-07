package com.trainer.srb.rus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme

@Composable
fun BottomNavBar(
    destinations: List<MainScreenDestination.Inner>,
    currentDestination: MainScreenDestination.Inner?,
    onDestinationChanged: (MainScreenDestination.Inner) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider()
        NavigationBar(
            tonalElevation = 0.dp
        ) {
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
}

@Preview(apiLevel = 33)
@Composable
fun BottomBarPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        BottomNavBar(
            destinations = MainScreenDestination.Inner.entries,
            currentDestination = MainScreenDestination.Inner.LEARN,
            onDestinationChanged = {}
        )
    }
}