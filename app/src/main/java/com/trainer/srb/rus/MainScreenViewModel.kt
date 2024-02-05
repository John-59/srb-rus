package com.trainer.srb.rus

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.trainer.srb.rus.feature.learn.navigation.navigateToLearn
import com.trainer.srb.rus.feature.dictionary.navigation.navigateToSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(): ViewModel() {

    val destinations = MainScreenDestination.entries

    var currentDestination by mutableStateOf(MainScreenDestination.LEARN)
        private set

    fun changeDestination(
        newDestination: MainScreenDestination,
        navController: NavHostController
    ) {
        when (newDestination) {
            MainScreenDestination.LEARN -> navController.navigateToLearn()
            MainScreenDestination.DICTIONARY -> navController.navigateToSearch()
            MainScreenDestination.STATISTICS -> {}//navController.navigateToHelp()
        }
        currentDestination = newDestination
    }
}